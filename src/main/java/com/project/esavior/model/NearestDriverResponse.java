package com.project.esavior.model;

public class NearestDriverResponse {
    private String driverId;
    private double latitude;
    private double longitude;
    private String distance;

    public NearestDriverResponse(String driverId, double latitude, double longitude, String distance) {
        this.driverId = driverId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    // Getters và Setters
}
