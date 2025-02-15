/**
 * Mikhail Filippov
 * 02/02/2025
 * API class for getting countries and anything to do
 * with the API
 */

package filippovm;

import com.google.gson.Gson;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class API {
    public static Countries call() {
        try {
            Countries countries;
            Gson gson = new Gson();
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://countries-api-abhishek.vercel.app/countries"))
                    .GET()
                    .build();
            HttpResponse<String> getResponse = HttpClient.newHttpClient()
                    .send(getRequest, BodyHandlers.ofString());

            countries = gson.fromJson(getResponse.body(), Countries.class);
            return countries;

        } catch (URISyntaxException e) {
            System.err.println("URI is invalid caught");
        } catch (IOException e) {
            System.err.println("IOException caught");
            System.exit(400);
        } catch (InterruptedException e) {
            System.err.println("InterruptedException caught");
        }
        return null;
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