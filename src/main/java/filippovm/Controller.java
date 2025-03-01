/**
 * Mikhail Filippov
 * 02/03/2025
 * Controller for GUI Application
 */

package filippovm;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Map;
import java.util.Optional;


public class Controller {
    public static final String CHOICE1 = "choice1",
            CHOICE2 = "choice2",
            CHOICE3 = "choice3",
            CHOICE4 = "choice4";
    public enum Status {
        INCORRECT,
        CORRECT
    }
    private static MediaPlayer correctSound;
    private static MediaPlayer wrongSound;

    @FXML
    private Button startButton, choice1, choice2, choice3, choice4;
    @FXML
    private ImageView flag;
    @FXML
    private Label questionLabel;
    @FXML
    private Label score_label, name_label, score;

    private Trivia generator;
    private Player player;

    @FXML
    private void initialize() {
        login();
        Countries countries = API.call();
        if (countries == null || countries.getStatusCode() != 200) {
            System.err.println("ERROR: API call failed");
            assert countries != null;
            System.err.println("Status code: " + countries.getStatusCode());
            return;
        }

        try {
            correctSound = new MediaPlayer(
                    new Media(getClass().getResource("/sounds/correct.mp3").toExternalForm())
            );
            wrongSound = new MediaPlayer(
                    new Media(getClass().getResource("/sounds/quack.mp3").toExternalForm())
            );
        } catch (NullPointerException e) {
            System.err.println("ERROR: Sound files not found");
        }

        generator = new Trivia(countries);
    }

    @FXML
    private void startGame() {
        startButton.setVisible(false);

        nextQuestion();

        choice1.setVisible(true);
        choice2.setVisible(true);
        choice3.setVisible(true);
        choice4.setVisible(true);
        flag.setVisible(true);
        score_label.setVisible(true);
        name_label.setVisible(true);
        score.setVisible(true);
    }

    private void login() {
        TextInputDialog loginPage = new TextInputDialog("");
        loginPage.getDialogPane().getButtonTypes().remove(1);
        Button login = (Button) loginPage.getDialogPane().lookupButton(ButtonType.OK);
        login.setText("Login");

        loginPage.setTitle("Login Page");
        loginPage.setHeaderText("""
                Welcome to Global Trivia! 
                Please enter your username.
                Allowed:
                   - 1-15 characters
                   - Alphanumeric characters
                   - Underscores
                Close this window to exit the game.
                """);
        loginPage.setContentText("Username:");
        ImageView loginPic = new ImageView(this.getClass().getResource("/images/user.png").toString());
        loginPic.setFitHeight(70);
        loginPic.setFitWidth(70);

        loginPage.setGraphic(loginPic);
        Optional<String> result = loginPage.showAndWait();
        if (result.isEmpty()) {
            System.exit(0);
        }
        if (!result.get().isEmpty()
                && result.get().length() < 16 && result.get().matches("(_*\\p{Alnum}_*)+")) {
            player = new Player(result.get());
            name_label.setText(result.get());
        } else {
            login();
        }
    }

    @FXML
    private void answer(ActionEvent event) {
        Button button = (Button) event.getSource();
        if (button.getText().equals(generator.getCorrectChoice())) {
            button.setId("correct");
            questionLabel.setText("Correct!");
            questionLabel.setId("correct");
            playSound(Status.CORRECT);
            player.incremenetScore();
        } else {
            button.setId("incorrect");
            questionLabel.setText("Uh oh!");
            questionLabel.setId("incorrect");
            playSound(Status.INCORRECT);
            player.decrementScore();
            showCorrectAnswer();
        }
        score.setText(String.valueOf(player.getScore()));
        transition();
    }

    private void setFlag(String url) {
        flag.setImage(API.convertSVGToImage(url));
    }
    private void showCorrectAnswer() {
        for (Button button : new Button[]{choice1, choice2, choice3, choice4}) {
            if (button.getText().equals(generator.getCorrectChoice())) {
                button.setId("correct");
                break;
            }
        }
    }
    private void nextQuestion() {
        for (Button button : new Button[]{choice1, choice2, choice3, choice4}) {
            button.setId("reset_button");
        }
        Map<String, String> question = generator.generateQuestion();

        setFlag(question.get("flag"));
        choice1.setText(question.get(CHOICE1));
        choice2.setText(question.get(CHOICE2));
        choice3.setText(question.get(CHOICE3));
        choice4.setText(question.get(CHOICE4));
    }

    private void transition() {
        questionLabel.setVisible(true);
        choice1.setDisable(true);
        choice2.setDisable(true);
        choice3.setDisable(true);
        choice4.setDisable(true);

        PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(2));
        pause.setOnFinished(e -> {
            questionLabel.setVisible(false);
            choice1.setDisable(false);
            choice2.setDisable(false);
            choice3.setDisable(false);
            choice4.setDisable(false);
            nextQuestion();
        });
        pause.play();
    }
    private void playSound(Status status) {
        MediaPlayer player = switch (status) {
            case CORRECT -> correctSound;
            case INCORRECT -> wrongSound;
        };
        player.seek(javafx.util.Duration.ZERO);
        player.play();
    }
}
