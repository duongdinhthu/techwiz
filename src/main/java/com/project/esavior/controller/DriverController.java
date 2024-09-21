package com.project.esavior.controller;

import com.project.esavior.dto.DriverDTO;
import com.project.esavior.model.Booking;
import com.project.esavior.model.Driver;
import com.project.esavior.service.BookingService;
import com.project.esavior.service.DriverService;
import com.project.esavior.websocket.WebSocketSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    @Autowired
    private WebSocketSessionManager webSocketSessionManager;
    @Autowired
    private DriverService driverService;
    @Autowired
    private BookingService bookingService;

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

    // Chuyển đổi từ Driver entity sang DTO
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

    // Lấy danh sách tài xế
    @GetMapping("/all")
    public List<DriverDTO> getAllDrivers() {
        List<Driver> drivers = driverService.getAllDrivers();
        return drivers.stream()
                .map(this::convertToDTO)
                .toList(); // Chuyển đổi sang DTO
    }

    // Quản lý tình trạng xe cứu thương
    @PutMapping("/{driverId}/status")
    public ResponseEntity<DriverDTO> updateDriverStatus(@PathVariable Integer driverId, @RequestParam String status) {
        try {
            Driver updatedDriver = driverService.updateDriverStatus(driverId, status);
            DriverDTO updatedDriverDTO = convertToDTO(updatedDriver); // Chuyển đổi sang DTO
            return ResponseEntity.ok(updatedDriverDTO);
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
    public ResponseEntity<DriverDTO> updateAmbulanceStatus(@PathVariable Integer driverId, @RequestParam String status) {
        try {
            Driver updatedDriver = driverService.updateDriverStatus(driverId, status);
            DriverDTO updatedDriverDTO = convertToDTO(updatedDriver); // Chuyển đổi sang DTO
            return ResponseEntity.ok(updatedDriverDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Tìm tài xế gần nhất
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
                .map(driver -> new DriverDTO(driver.getDriverId(), driver.getDriverPhone(), driver.getDriverName(), driver.getLongitude(), driver.getLatitude()))
                .toList();

        // Trả về danh sách DTO
        return ResponseEntity.ok(driverDTOs);
    }

    @PutMapping("/accept-booking/{bookingId}")
    public ResponseEntity<?> acceptBooking(@PathVariable Integer bookingId, @RequestBody Map<String, Object> requestData) throws IOException {
        Integer driverId = (Integer) requestData.get("driverId");

        // Tìm booking theo bookingId
        Optional<Booking> bookingOptional = bookingService.findBookingById(bookingId);
        if (!bookingOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
        }

        Booking booking = bookingOptional.get();

        // Tìm tài xế theo driverId
        Optional<Driver> driverOptional = driverService.findDriverById(driverId);
        if (!driverOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Driver not found");
        }

        Driver driver = driverOptional.get();

        // Cập nhật vị trí của tài xế theo điểm đón của khách hàng
        driver.setLatitude(booking.getLatitude());
        driver.setLongitude(booking.getLongitude());

        // Lưu thông tin tài xế đã cập nhật
        driverService.save(driver);

        // Cập nhật trạng thái booking
        booking.setBookingStatus("Accepted");
        booking.setDriver(driver);  // Gán tài xế cho booking
        bookingService.save(booking);

        // Gửi thông báo tới khách hàng hoặc tài xế qua WebSocket
        WebSocketSession session = webSocketSessionManager.getSession(driver.getDriverId());
        if (session != null && session.isOpen()) {
            String message = "Tài xế đã chấp nhận đơn hàng. Thông tin điểm đón: " + booking.getPickupAddress();
            session.sendMessage(new TextMessage(message));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("WebSocket session is not available");
        }
        return ResponseEntity.ok("Booking accepted and driver location updated");
    }


}
