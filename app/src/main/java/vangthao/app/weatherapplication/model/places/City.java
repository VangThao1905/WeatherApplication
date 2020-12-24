package vangthao.app.weatherapplication.model.places;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "city_table")
public class City implements Serializable {
    @PrimaryKey
    private int id;
    private String lat;
    private String lon;
    private String name;

    public City(int id, String lat, String lon, String name) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.name = name;
    }

    @Ignore
    public City(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
