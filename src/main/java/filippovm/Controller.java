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

import java.util.Map;


public class Controller {
    public static final String CHOICE1 = "choice1",
            CHOICE2 = "choice2",
            CHOICE3 = "choice3",
            CHOICE4 = "choice4";

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
    private void answer(ActionEvent event) {
        Button button = (Button) event.getSource();
        if (button.getId().equals(generator.getCorrectChoice())) {
            //button.setId("correct");
            button.getStyleClass().remove("reset_button");
            button.getStyleClass().add("correct");
            questionLabel.setText("Correct!");
            questionLabel.setId("correct");
        } else {
            //button.setId("incorrect");
            button.getStyleClass().remove("reset_button");
            button.getStyleClass().add("incorrect");
            questionLabel.setText("Uh oh!");
            questionLabel.setId("incorrect");
            showCorrectAnswer();
        }
        transition();
        // TODO: add sound effects
    }

    private void setFlag(String url) {
        flag.setImage(API.convertSVGToImage(url));
    }
    private void showCorrectAnswer() {
        switch (generator.getCorrectChoice()) {
            case CHOICE1 -> choice1.setId("correct");
            case CHOICE2 -> choice2.setId("correct");
            case CHOICE3 -> choice3.setId("correct");
            case CHOICE4 -> choice4.setId("correct");
        }
    }
    private void nextQuestion() {
        for (Button button : new Button[]{choice1, choice2, choice3, choice4}) {
            //button.getStyleClass().remove("correct");
            //button.getStyleClass().remove("incorrect");
            button.getStyleClass().add("reset_button");
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
}
