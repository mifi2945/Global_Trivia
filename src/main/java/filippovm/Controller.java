/**
 * Mikhail Filippov
 * 02/03/2025
 * Controller for GUI Application
 */

package filippovm;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

        Map<String, String> question = generator.generateQuestion();

        setFlag(question.get("flag"));
        choice1.setText(question.get(CHOICE1));
        choice2.setText(question.get(CHOICE2));
        choice3.setText(question.get(CHOICE3));
        choice4.setText(question.get(CHOICE4));


        choice1.setVisible(true);
        choice2.setVisible(true);
        choice3.setVisible(true);
        choice4.setVisible(true);
        flag.setVisible(true);


    }

    private void setFlag(String url) {
        flag.setImage(API.convertSVGToImage(url));
    }
}
