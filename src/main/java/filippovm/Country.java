package filippovm;

import java.util.ArrayList;
import java.util.List;

public class Country {
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

//    static class Data{
//        String name;
//        String capital;
//        List<String> borders = new ArrayList<>();
//
//        public String getName() {
//            return name;
//        }
//        public void setName(String name) {
//            this.name = name;
//        }
//        public String getCapital() {
//            return capital;
//        }
//        public void setCapital(String capital) {
//            this.capital = capital;
//        }
//        public List<String> getBorders() {
//            return borders;
//        }
//        public void setBorders(List<String> borders) {
//            this.borders = borders;
//        }
//    }

//    private Data data;
//    public Data getData() {
//        return data;
//    }
//
//    public void setData(Data data) {
//        this.data = data;
//    }

}
