package com.project.esavior.controller;

import com.project.esavior.model.Booking;
import com.project.esavior.model.Patients;
import com.project.esavior.service.BookingService;
import com.project.esavior.service.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final PatientsService patientsService;

    @Autowired
    public BookingController(BookingService bookingService, PatientsService patientsService) {
        this.bookingService = bookingService;
        this.patientsService = patientsService;
    }
    @PostMapping("/emergency")
    public ResponseEntity<Booking> createEmergencyBooking(@RequestBody Booking bookingRequest) {
        // Debug: In ra JSON request để kiểm tra
        System.out.println("Booking Request: " + bookingRequest);
        System.out.println(bookingRequest.getPatient().getEmail());

        // Kiểm tra xem đối tượng patient có null không
        if (bookingRequest.getPatient() == null || bookingRequest.getPatient().getEmail() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Yêu cầu không hợp lệ
        }

        // Tìm bệnh nhân theo email
        Optional<Patients> patientOpt = patientsService.findByEmail(bookingRequest.getPatient().getEmail());
        if (patientOpt.isPresent()) {
            System.out.println("Patient found: " + patientOpt.get());
        } else {
            System.out.println("Patient not found with email: " + bookingRequest.getPatient().getEmail());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Tạo đối tượng Booking mới
        Booking newBooking = new Booking();
        newBooking.setPatient(patientOpt.get()); // Lấy đối tượng Patients từ Optional
        newBooking.setBookingType("Provide");
        newBooking.setPickupAddress(bookingRequest.getPickupAddress());
        newBooking.setPickupTime(LocalDateTime.now()); // Thời gian đặt hiện tại
        newBooking.setBookingStatus("Pending");
        newBooking.setLatitude(bookingRequest.getLatitude());
        newBooking.setLongitude(bookingRequest.getLongitude());
        newBooking.setDestinationLatitude(bookingRequest.getDestinationLatitude());
        newBooking.setDestinationLongitude(bookingRequest.getDestinationLongitude());
        newBooking.setCost(bookingRequest.getCost());
        Booking savedBooking = bookingService.createBooking(newBooking);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

    // Tạo đặt chỗ mới
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking newBooking = bookingService.createBooking(booking);
        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
    }

    // Lấy danh sách đặt chỗ
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    // Tìm kiếm chi tiết đặt chỗ theo tên bệnh viện
    @GetMapping("/hospital")
    public List<Booking> getBookingsByHospitalName(@RequestParam String hospitalName) {
        return bookingService.findByHospitalName(hospitalName);
    }

    // Tìm kiếm chi tiết đặt chỗ theo thành phố
    @GetMapping("/city") .
    public List<Booking> getBookingsByCityName(@RequestParam String cityName) {
        return bookingService.findByCityName(cityName);
    }

    // Tìm kiếm theo tên bệnh viện và thành phố
    @GetMapping("/search")
    public List<Booking> getBookingsByHospitalAndCity(@RequestParam String hospitalName, @RequestParam String cityName) {
        return bookingService.findByHospitalNameAndCityName(hospitalName, cityName);
    }

    // Tìm kiếm tất cả đặt chỗ liên quan đến một từ khóa (search)
    @GetMapping("/keyword")
    public List<Booking> searchBookings(@RequestParam String keyword) {
        return bookingService.searchBookings(keyword);
    }

    // Lấy chi tiết đặt chỗ theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Integer id) {
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Cập nhật thông tin đặt chỗ
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Integer id, @RequestBody Booking updatedBooking) {
        Booking booking = bookingService.updateBooking(id, updatedBooking);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Xóa đặt chỗ
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Integer id) {
        boolean deleted = bookingService.deleteBooking(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Bán kính Trái Đất tính bằng km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // khoảng cách tính bằng km
        return distance;
    }
    @PostMapping("/calculate-cost")
    public ResponseEntity<Map<String, Object>> calculateCost(@RequestBody Map<String, Object> locationData) {
        // Lấy tọa độ từ request
        double startLatitude = (double) locationData.get("startLatitude");
        double startLongitude = (double) locationData.get("startLongitude");
        double destinationLatitude = (double) locationData.get("destinationLatitude");
        double destinationLongitude = (double) locationData.get("destinationLongitude");

        // Tính khoảng cách giữa điểm đi và điểm đến
        double distance = calculateDistance(startLatitude, startLongitude, destinationLatitude, destinationLongitude);

        // Giả sử mỗi km có giá là 1 USD
        double costPerKmUSD = 1.5; // Chi phí 1 USD cho mỗi km
        double costInUSD = distance * costPerKmUSD;

        // Tạo response với chi phí
        Map<String, Object> response = new HashMap<>();
        response.put("distance", distance); // Khoảng cách tính bằng km
        response.put("costInUSD", costInUSD); // Chi phí tính bằng USD

        return ResponseEntity.ok(response);
    }


}
