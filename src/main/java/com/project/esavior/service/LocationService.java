package com.project.esavior.service;

import com.project.esavior.model.Location;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    private double latitude;
    private double longitude;

    // Cập nhật vị trí
    public void updateLocation(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    // Lấy vị trí hiện tại
    public String getCurrentLocation() {
        return "Latitude: " + latitude + ", Longitude: " + longitude;
    }
}
