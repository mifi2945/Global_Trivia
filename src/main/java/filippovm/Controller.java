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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;
import java.util.Map;


public class Controller {
    public static final String CHOICE1 = "choice1",
            CHOICE2 = "choice2",
            CHOICE3 = "choice3",
            CHOICE4 = "choice4";
    public enum Status {
        CORRECT,
        INCORRECT
    }

    @FXML
    private Button startButton, choice1, choice2, choice3, choice4;
    @FXML
    private ImageView flag;
    @FXML
    private Label questionLabel;

    private Trivia generator;

    @FXML
    private void initialize() {
        Countries countries = API.call();
        if (countries == null || countries.getStatusCode() != 200) {
            System.err.println("ERROR: API call failed");
            assert countries != null;
            System.err.println("Status code: " + countries.getStatusCode());
            return;
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
    }

    @FXML
    private void answer(ActionEvent event) throws URISyntaxException {
        Button button = (Button) event.getSource();
        if (button.getText().equals(generator.getCorrectChoice())) {
            button.setId("correct");
            questionLabel.setText("Correct!");
            questionLabel.setId("correct");
            playSound(Status.CORRECT);
        } else {
            button.setId("incorrect");
            questionLabel.setText("Uh oh!");
            questionLabel.setId("incorrect");
            playSound(Status.INCORRECT);
            showCorrectAnswer();
        }
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
        String soundPath = switch (status) {
            case CORRECT -> "/sounds/correct.mp3";
            case INCORRECT -> "/sounds/quack.mp3";
        };
        try {
            new MediaPlayer(new Media(getClass().getResource(soundPath).toExternalForm())).play();
        } catch (NullPointerException e) {
            System.err.println("ERROR: unable to play sound");
            System.err.println(e.getMessage());
        }
    }
}
