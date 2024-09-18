package com.project.esavior.controller;

import com.project.esavior.model.Booking;
import com.project.esavior.model.Driver;
import com.project.esavior.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    // Đăng nhập tài xế
    @PostMapping("/login")
    public ResponseEntity<String> loginDriver(@RequestParam String email, @RequestParam String password) {
        Driver authenticatedDriver = driverService.authenticateDriver(email, password);
        if (authenticatedDriver != null) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }

    // Quản lý tình trạng xe cứu thương
    @PutMapping("/{driverId}/status")
    public ResponseEntity<Driver> updateDriverStatus(@PathVariable Integer driverId, @RequestParam String status) {
        try {
            Driver updatedDriver = driverService.updateDriverStatus(driverId, status);
            return ResponseEntity.ok(updatedDriver);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Cập nhật trạng thái đặt chỗ
    @PutMapping("/bookings/{bookingId}/status")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable Integer bookingId, @RequestParam String status) {
        try {
            Booking updatedBooking = driverService.updateBookingStatus(bookingId, status);
            return ResponseEntity.ok(updatedBooking);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Cập nhật trạng thái xe cứu thương
    @PutMapping("/{driverId}/ambulance/status")
    public ResponseEntity<Driver> updateAmbulanceStatus(@PathVariable Integer driverId, @RequestParam String status) {
        try {
            Driver updatedDriver = driverService.updateDriverStatus(driverId, status);
            return ResponseEntity.ok(updatedDriver);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
