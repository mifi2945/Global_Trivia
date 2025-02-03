package filippovm;

import java.util.ArrayList;
import java.util.List;

public class Weather {

    private class WeatherData {
        private int temp2m;
        private String weather;

        public int getTemp2m() {
            return temp2m;
        }
        public void setTemp2m(int temp2m) {
            this.temp2m = temp2m;
        }
        public String getWeather() {
            return weather;
        }
        public void setWeather(String weather) {
            this.weather = weather;
        }
    }

    private String init;
    private List<WeatherData> dataseries = new ArrayList<>();

    public int getTemp2m() {
        return dataseries.getFirst().getTemp2m();
    }

    public void setTemp2m(int temp2m) {
        dataseries.getFirst().temp2m = temp2m;
    }

    public String getWeather() {
        return dataseries.getFirst().getWeather();
    }

    public void setWeather(String weather) {
        dataseries.getFirst().weather = weather;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }
    public List<WeatherData> getDataseries() {
        return dataseries;
    }

    public void setDataseries(List<WeatherData> dataseries) {
        this.dataseries = dataseries;
    }
}
