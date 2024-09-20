package com.project.esavior.controller;

import com.project.esavior.model.Location;
import com.project.esavior.model.LocationRequest;
import com.project.esavior.model.NearestDriverRequest;
import com.project.esavior.model.NearestDriverResponse;
import com.project.esavior.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    // Inject Location service to handle logic
    @Autowired
    private LocationService locationService;

    // API để cập nhật vị trí của khách hàng/tài xế
    @PostMapping("/update")
    public ResponseEntity<String> updateLocation(@RequestBody LocationRequest request) {
        boolean updated = locationService.updateLocation(request);
        if (updated) {
            return ResponseEntity.ok("Location updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update location");
        }
    }

    // API để lấy vị trí của khách hàng/tài xế
    @GetMapping("/get")
    public ResponseEntity<Location> getLocation(@RequestParam String userId, @RequestParam String role) {
        Location location = locationService.getLocation(userId, role);
        if (location != null) {
            return ResponseEntity.ok(location);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // API để tìm tài xế gần nhất
    @PostMapping("/nearest-driver")
    public ResponseEntity<NearestDriverResponse> findNearestDriver(@RequestBody NearestDriverRequest request) {
        NearestDriverResponse response = locationService.findNearestDriver(request);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
