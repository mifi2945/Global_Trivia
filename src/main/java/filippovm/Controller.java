/**
 * Mikhail Filippov
 * 02/03/2025
 * Controller for GUI Application
 */

package filippovm;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Controller {
    @FXML
    private Button startButton, choice1, choice2, choice3, choice4;
    @FXML
    private ImageView flag;

    @FXML
    private void startGame() {
        startButton.setVisible(false);

        Countries countries = API.call();
        if (countries == null || countries.getStatusCode() != 200) {
            System.err.println("ERROR: API call failed");
            assert countries != null;
            System.err.println("Status code: " + countries.getStatusCode());
            return;
        }

        int random = (int)(Math.random() * countries.getCountries().size());
        Country country = countries.getCountries().get(random);

        setFlag(country.getFlag());


        int randomChoice = 0;
        while (randomChoice < 4 && randomChoice < country.getBorders().size()) {
            switch (randomChoice) {
                case 0:
                    choice1.setText(country.getBorders().get(randomChoice));
                    break;
                case 1:
                    choice2.setText(country.getBorders().get(randomChoice));
                    break;
                case 2:
                    choice3.setText(country.getBorders().get(randomChoice));
                    break;
                case 3:
                    choice4.setText(country.getBorders().get(randomChoice));
                    break;
            }
            randomChoice++;
        }

        if (randomChoice < 4) {
            for (int i = randomChoice; i < 4; i++) {
                switch (i) {
                    case 0:
                        choice1.setText(countries.getCountries().get((int)(Math.random() * countries.getCountries().size())).getName());
                        break;
                    case 1:
                        choice2.setText(countries.getCountries().get((int)(Math.random() * countries.getCountries().size())).getName());
                        break;
                    case 2:
                        choice3.setText(countries.getCountries().get((int)(Math.random() * countries.getCountries().size())).getName());
                        break;
                    case 3:
                        choice4.setText(countries.getCountries().get((int)(Math.random() * countries.getCountries().size())).getName());
                        break;
                }
            }
        }

        choice1.setVisible(true);
        choice2.setVisible(true);
        choice3.setVisible(true);
        choice4.setVisible(true);
        flag.setVisible(true);


    }

    private void setFlag(String url) {
        flag.setImage(new Image("")); // TODO add SVG file support
    }
}
