package com.example.weather.model;

import javax.persistence.*;

@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cityId;
    private String cityName;
    private Double temperature;
    private double lon;
    private double lat;

    public Weather() {}

    public Weather(int cityId, String cityName, Double temperature, double lon, double lat) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.temperature = temperature;
        this.lon = lon;
        this.lat = lat;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public int getCityId() {
        return cityId;
    }
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public Double getTemperature() {
        return temperature;
    }
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
    public double getLon() {
        return lon;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }
    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
}
