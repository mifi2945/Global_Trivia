package filippovm;

import java.util.ArrayList;
import java.util.List;

public class Countries {
    private List<Country> data = new ArrayList<>();
    private int statusCode;

    public List<Country> getCountries() {
        return data;
    }
    public void setCountries(List<Country> data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
