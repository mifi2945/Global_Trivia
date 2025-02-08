package filippovm;

import java.util.*;

public class Trivia {
    private Set<Country> usedCountries;
    private final List<Country> countries;
    private String correctChoice;

    public static int TOTAL_COUNTRIES;

    public Trivia(Countries countries) {
        usedCountries = new HashSet<>();
        this.countries = countries.getCountries();
        correctChoice = "";

        TOTAL_COUNTRIES = countries.getCountries().size();
    }

    public Map<String, String> generateQuestion() {
        Map<String, String> question = new HashMap<>();

        // get countryIndex country
        int countryIndex;
        do {
            countryIndex = (int) (Math.random() * TOTAL_COUNTRIES);
        } while (!usedCountries.add(countries.get(countryIndex)));

        Country country = countries.get(countryIndex);
        // add flag url
        question.put("flag", country.getFlag());

        List<Integer> usedIndexes = new ArrayList<>(Arrays.asList(1,2,3,4));
        Random random = new Random();
        int randomChoice = random.nextInt(1, 5);

        // add correct answer
        question.put("choice"+randomChoice, country.getName());
        correctChoice = country.getName();
        usedIndexes.remove(randomChoice-1);

        // add wrong answers, starting with borders
        List<String> usedBorders = country.getBorders();
        while (!usedBorders.isEmpty() && !usedIndexes.isEmpty()) {
            int incorrectRandom = random.nextInt(usedBorders.size());
            question.put("choice"+usedIndexes.removeFirst(), usedBorders.remove(incorrectRandom));
        }
        while (!usedIndexes.isEmpty()) {
            int incorrectRandom = random.nextInt(TOTAL_COUNTRIES);
            String incorrectCountry = countries.get(incorrectRandom).getName();
            if (incorrectCountry.equals(country.getName()) || question.containsValue(incorrectCountry)) {
                continue;
            }
            question.put("choice"+usedIndexes.removeFirst(), incorrectCountry);
        }

        return question;
    }

    public String getCorrectChoice() {
        return correctChoice;
    }
}
