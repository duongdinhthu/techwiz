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
import java.util.List;
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

        // Lưu đặt chỗ mới
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
    @GetMapping("/city")
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
}
