package filippovm;

import java.util.ArrayList;
import java.util.List;

public class Country implements Comparable<Country> {
    private String name;
    private String capital;
    private List<String> borders = new ArrayList<>();
    private String flag;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCapital() {
        return capital;
    }
    public void setCapital(String capital) {
        this.capital = capital;
    }
    public List<String> getBorders() {
        return borders;
    }
    public void setBorders(List<String> borders) {
        this.borders = borders;
    }
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public int compareTo(Country o) {
        return this.name.compareTo(o.name);
    }
}
