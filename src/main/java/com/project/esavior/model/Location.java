package com.project.esavior.model;

import java.time.LocalDateTime;

public class Location {
    private String userId;
    private String role; // customer or driver
    private double latitude;
    private double longitude;
    private LocalDateTime updatedAt;

    public Location() {
    }

    public Location(String userId, String role, double latitude, double longitude, LocalDateTime updatedAt) {
        this.userId = userId;
        this.role = role;
        this.latitude = latitude;
        this.longitude = longitude;
        this.updatedAt = updatedAt;
    }

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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
// Constructors, getters, and setters
}