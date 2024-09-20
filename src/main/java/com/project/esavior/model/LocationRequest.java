package com.project.esavior.model;

public class LocationRequest {
    private String userId;
    private String role;
    private double latitude;
    private double longitude;

    // Constructor
    public LocationRequest(String userId, String role, double latitude, double longitude) {
        this.userId = userId;
        this.role = role;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters và Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
