package com.project.esavior.controller;

import com.project.esavior.dto.BookingDTO;
import com.project.esavior.dto.DriverDTO;
import com.project.esavior.model.Booking;
import com.project.esavior.model.Driver;
import com.project.esavior.model.Patients;
import com.project.esavior.service.BookingService;
import com.project.esavior.service.DriverService;

import com.project.esavior.service.LocationService;
import com.project.esavior.service.PatientsService;
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

    private DriverService driverService;
    private BookingService bookingService;
    private LocationService locationService;
    private PatientsService patientsService;
    public DriverController(LocationService locationService,BookingService bookingService,DriverService driverService,PatientsService patientsService) {
        this.locationService = locationService;
        this.bookingService = bookingService;
        this.driverService = driverService;
        this.patientsService = patientsService;
    }


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

        // Chuyển đổi danh sách Driver thành danh sách DriverDTO
        List<DriverDTO> driverDTOs = nearestDrivers.stream()
                .map(driver -> new DriverDTO(driver.getDriverId(), driver.getDriverPhone(), driver.getDriverName(), driver.getLongitude(), driver.getLatitude()))
                .toList();

        // Lấy tài xế đầu tiên trong danh sách gần nhất
        Driver nearestDriver = nearestDrivers.get(0);

        // Lưu driverId vào LocationService
        locationService.updateDriverId(nearestDriver.getDriverId());

        // Cập nhật booking với driverId và vị trí khách hàng
        bookingService.saveBookingForDriver(nearestDriver.getDriverId(), latitude, longitude);

        // Trả về danh sách tài xế gần nhất (DTOs)
        return ResponseEntity.ok(driverDTOs);
    }

    @GetMapping("/check-booking/{driverId}")
    public ResponseEntity<BookingDTO> checkForNewBooking(@PathVariable Integer driverId) {
        // Lấy booking mới nhất của tài xế từ BookingService
        Optional<Booking> newBooking = bookingService.getBookingForDriver(driverId);

        if (newBooking.isPresent()) {
            Booking booking = newBooking.get();

            // Lấy thông tin bệnh nhân từ patientId
            Patients patient = patientsService.getPatientById(booking.getPatient().getPatientId());
            String patientName = patient.getPatientName();
            String patientPhone = patient.getPhoneNumber();

            // Tạo BookingDTO với thông tin cần thiết
            BookingDTO bookingDTO = new BookingDTO(
                    booking.getLatitude(),           // Vĩ độ điểm đón
                    booking.getLongitude(),          // Kinh độ điểm đón
                    booking.getDestinationLatitude(),// Vĩ độ điểm đến
                    booking.getDestinationLongitude(),// Kinh độ điểm đến
                    booking.getPickupAddress(),      // Địa chỉ điểm đón
                    patientName,                     // Tên bệnh nhân
                    patientPhone                     // Số điện thoại bệnh nhân
            );

            return ResponseEntity.ok(bookingDTO);
        } else {
            return ResponseEntity.noContent().build();  // Không có đơn đặt xe mới
        }
    }
//    @PostMapping("/nearest")
//    public ResponseEntity<List<DriverDTO>> findNearestDrivers(@RequestBody Map<String, Double> location) {
//        double latitude = location.get("latitude");
//        double longitude = location.get("longitude");
//
//        // Tìm các tài xế gần nhất
//        List<Driver> nearestDrivers = driverService.findNearestDrivers(latitude, longitude);
//
//        // Nếu không có tài xế gần nhất
//        if (nearestDrivers.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//
//        // Chuyển đổi danh sách Driver thành danh sách DriverDTO với chỉ tên và số điện thoại
//        List<DriverDTO> driverDTOs = nearestDrivers.stream()
//                .map(driver -> new DriverDTO(driver.getDriverId(), driver.getDriverPhone(), driver.getDriverName(), driver.getLongitude(), driver.getLatitude()))
//                .toList();
//
//        // Trả về danh sách tài xế gần nhất (DTOs)
//        return ResponseEntity.ok(driverDTOs);
//    }

}
