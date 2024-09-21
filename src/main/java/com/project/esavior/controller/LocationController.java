package com.project.esavior.controller;

import com.project.esavior.model.Location;
import com.project.esavior.service.LocationService; // Service để xử lý logic
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
