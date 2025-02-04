/**
 * Mikhail Filippov
 * 02/03/2025
 * Controller for GUI Application
 */

package filippovm;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Set;
import java.util.TreeSet;


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

        Set<Country> usedCountries = new TreeSet<>();
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
        // TODO add SVG file support
        flag.setImage(convertSVGToImage(url));
    }

    public static Image convertSVGToImage(String url) {
        try {
            // Read SVG from URL
            InputStream inputStream = new URL(url).openStream();
            TranscoderInput input = new TranscoderInput(inputStream);

            // Convert SVG to PNG using Batik
            PNGTranscoder transcoder = new PNGTranscoder();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outputStream);

            transcoder.transcode(input, output);
            outputStream.flush();

            // Convert PNG byte stream to JavaFX Image
            ByteArrayInputStream pngInputStream = new ByteArrayInputStream(outputStream.toByteArray());
            BufferedImage bufferedImage = ImageIO.read(pngInputStream);
            return SwingFXUtils.toFXImage(bufferedImage, null);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
