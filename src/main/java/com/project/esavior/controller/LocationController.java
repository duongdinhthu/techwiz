package com.project.esavior.controller;

import com.project.esavior.model.Driver;
import com.project.esavior.model.Location;
import com.project.esavior.service.LocationService; // Service để xử lý logic
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService; // Inject service

    @PostMapping("/update")
    public ResponseEntity<String> updateLocation(@RequestBody Location location) {
        try {
            // Gọi service để lưu vị trí
            locationService.updateLocation(location);
            return new ResponseEntity<>("Location updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating location: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/update-location")
    public ResponseEntity<String> updateDriverLocation(@RequestBody Map<String, Object> request) {
        Integer driverId = (Integer) request.get("driverId");
        Double latitude = (Double) request.get("latitude");
        Double longitude = (Double) request.get("longitude");

        Location location = new Location(latitude, longitude);
        locationService.updateDriverLocation(driverId, location);  // Cập nhật vị trí tài xế
        System.out.println("Updating location for driverId: " + driverId + " with lat: " + latitude + ", long: " + longitude);

        return new ResponseEntity<>("Location updated successfully", HttpStatus.OK);
    }
    @PostMapping("/location")
    public ResponseEntity<Map<String, Object>> getDriverLocation(@RequestBody Driver request) {
        Integer driverId = request.getDriverId();
        Location location = locationService.getDriverLocation(driverId);  // Lấy vị trí tài xế

        if (location != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("latitude", location.getLatitude());
            response.put("longitude", location.getLongitude());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/get-driver-location/{driverId}")
    public ResponseEntity<Location> getDriverLocation(@PathVariable Integer driverId) {
        Location location = locationService.getDriverLocation(driverId);
        if (location != null) {
            System.out.println("Returning location for driverId: " + driverId);
            return new ResponseEntity<>(location, HttpStatus.OK);
        } else {
            System.out.println("No location found for driverId: " + driverId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
