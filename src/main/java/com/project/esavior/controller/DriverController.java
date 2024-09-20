package com.project.esavior.controller;

import com.project.esavior.dto.DriverDTO;
import com.project.esavior.model.Booking;
import com.project.esavior.model.Driver;
import com.project.esavior.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    // Đăng nhập tài xế
    @PostMapping("/login")
    public ResponseEntity<?> loginDriver(@RequestBody Map<String, String> loginRequest) {
        String driverEmail = loginRequest.get("email");
        String driverPassword = loginRequest.get("password");

        // Xác thực tài xế
        Driver authenticatedDriver = driverService.authenticateDriver(driverEmail, driverPassword);

        if (authenticatedDriver != null) {
            // Chuyển đổi Driver sang DriverDTO
            DriverDTO driverDTO = convertToDTO(authenticatedDriver);

            // Trả về đối tượng DriverDTO
            return ResponseEntity.ok(driverDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("success", false));
        }


    }
    public DriverDTO convertToDTO(Driver driver) {
        DriverDTO dto = new DriverDTO();
        dto.setDriverId(driver.getDriverId());
        dto.setDriverName(driver.getDriverName());
        dto.setEmail(driver.getEmail());
        dto.setDriverPhone(driver.getDriverPhone());
        dto.setLicenseNumber(driver.getLicenseNumber());
        dto.setStatus(driver.getStatus());
        dto.setLatitude(driver.getLatitude());
        dto.setLongitude(driver.getLongitude());
        if (driver.getHospital() != null) {
            dto.setHospitalId(driver.getHospital().getHospitalId());
        }
        dto.setCreatedAt(driver.getCreatedAt());
        dto.setUpdatedAt(driver.getUpdatedAt());
        return dto;
    }

    @GetMapping("/all")
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
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
    @PostMapping("/nearest")
    public ResponseEntity<List<DriverDTO>> findNearestDrivers(@RequestBody Map<String, Double> location) {
        double latitude = location.get("latitude");
        double longitude = location.get("longitude");

        // Tìm các tài xế gần nhất
        List<Driver> nearestDrivers = driverService.findNearestDrivers(latitude, longitude);

        // Nếu không có tài xế gần nhất
        if (nearestDrivers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Chuyển đổi danh sách Driver thành danh sách DriverDTO với chỉ tên và số điện thoại
        List<DriverDTO> driverDTOs = nearestDrivers.stream()
                .map(driver -> new DriverDTO(driver.getDriverName(), driver.getDriverPhone()))
                .toList();

        // Trả về danh sách DTO

        return ResponseEntity.ok(driverDTOs);
    }


}
