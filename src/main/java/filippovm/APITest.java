/**
 * Mikhail Filippov
 * 02/02/2025
 * PLaying around with Maven and Java APIs
 */

package filippovm;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class APITest {
    public static void main(String[] args) {
        try {
            Weather weather = new Weather();
            Gson gson = new Gson();
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://www.7timer.info/bin/api.pl?lon=113.17&lat=23.09&product=civil&output=json"))
                    .GET()
                    .build();

            HttpResponse<String> getResponse = HttpClient.newHttpClient()
                    .send(getRequest, BodyHandlers.ofString());

            weather = gson.fromJson(getResponse.body(), Weather.class);

//            System.out.println(getResponse.body());
            System.out.println("Temperature: " + weather.getTemp2m());
            System.out.println("Weather: " + weather.getWeather());
            System.out.println("Init: " + weather.getInit());

        } catch (URISyntaxException e) {
            System.err.println("URI is invalid");
        } catch (IOException e) {
            System.err.println("IOException");
        } catch (InterruptedException e) {
            System.err.println("InterruptedException");
        }
    }
}