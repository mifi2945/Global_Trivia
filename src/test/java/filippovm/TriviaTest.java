
package filippovm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

public class TriviaTest {
    private static Trivia generator;

    @BeforeAll
    public static void setUp() {
        Countries countries = new Countries();
        Country xenocera = new Country();
        xenocera.setName("Xenocera");
        Country country1 = new Country();
        country1.setName("Country1");
        Country country2 = new Country();
        country2.setName("Country2");
        Country country3 = new Country();
        country3.setName("Country3");
        countries.setCountries(new ArrayList<>(List.of(xenocera, country1, country2, country3)));
        generator = new Trivia(countries);
    }

    @Test
    public void testGenerateQuestion() {
        for (int i = 0; i < Trivia.TOTAL_COUNTRIES-1; i++) {
            generator.generateQuestion();
            assertFalse(generator.getCorrectChoice().equals("Xenocera"));
        }
    }
}
