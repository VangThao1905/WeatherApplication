package vangthao.app.weatherapplication.model.places;

import java.io.Serializable;

public class CitySave implements Serializable {
    private City city;
    private String email;

    public CitySave(City city, String email) {
        super();
        this.city = city;
        this.email = email;
    }

    public CitySave() {

    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
