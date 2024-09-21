package com.project.esavior.controller;

import com.project.esavior.dto.BookingDTO;
import com.project.esavior.model.Booking;
import com.project.esavior.model.Driver;
import com.project.esavior.model.Patients;
import com.project.esavior.service.BookingService;
import com.project.esavior.service.DriverService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final DriverService driverService;

    private final BookingService bookingService;
    private final PatientsService patientsService;

    @Autowired
    public BookingController(BookingService bookingService, PatientsService patientsService, DriverService driverService) {
        this.bookingService = bookingService;
        this.patientsService = patientsService;
        this.driverService = driverService;
    }

    @PostMapping("/emergency")
    public ResponseEntity<Booking> createEmergencyBooking(@RequestBody Booking bookingRequest) {
        // Debug: In ra JSON request để kiểm tra
        System.out.println("Booking Request: " + bookingRequest);
        System.out.println(bookingRequest.getPatient().getEmail());

        if (bookingRequest.getPatient() == null || bookingRequest.getPatient().getEmail() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Yêu cầu không hợp lệ
        }

        Optional<Patients> patientOpt = patientsService.findByEmail(bookingRequest.getPatient().getEmail());
        if (patientOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Booking newBooking = new Booking();
        newBooking.setPatient(patientOpt.get());
        newBooking.setBookingType("Provide");
        newBooking.setPickupAddress(bookingRequest.getPickupAddress());
        newBooking.setPickupTime(LocalDateTime.now());
        newBooking.setBookingStatus("Pending");
        newBooking.setLatitude(bookingRequest.getLatitude());
        newBooking.setLongitude(bookingRequest.getLongitude());
        newBooking.setDestinationLatitude(bookingRequest.getDestinationLatitude());
        newBooking.setDestinationLongitude(bookingRequest.getDestinationLongitude());
        newBooking.setCost(bookingRequest.getCost());
        newBooking.setAmbulanceType(bookingRequest.getAmbulanceType());

        Booking savedBooking = bookingService.createBooking(newBooking);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/non-emergency")
    public ResponseEntity<BookingDTO> createNonEmergencyBooking(@RequestBody Booking bookingRequest) {
        if (bookingRequest.getPatient() == null || bookingRequest.getPatient().getEmail() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Yêu cầu không hợp lệ
        }

        Optional<Patients> patientOpt = patientsService.findByEmail(bookingRequest.getPatient().getEmail());
        if (patientOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Booking newBooking = new Booking();
        newBooking.setPatient(patientOpt.get());
        newBooking.setPickupAddress(bookingRequest.getPickupAddress());
        newBooking.setPickupTime(bookingRequest.getPickupTime());
        newBooking.setBookingStatus("Pending");
        newBooking.setLatitude(bookingRequest.getLatitude());
        newBooking.setLongitude(bookingRequest.getLongitude());
        newBooking.setDestinationLatitude(bookingRequest.getDestinationLatitude());
        newBooking.setDestinationLongitude(bookingRequest.getDestinationLongitude());
        newBooking.setCost(bookingRequest.getCost());
        newBooking.setBookingType(bookingRequest.getBookingType());

        Booking savedBooking = bookingService.createBooking(newBooking);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Tạo đặt chỗ mới
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking newBooking = bookingService.createBooking(booking);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Lấy danh sách đặt chỗ
    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookings);
    }

    // Tìm kiếm chi tiết đặt chỗ theo tên bệnh viện
    @GetMapping("/hospital")
    public List<BookingDTO> getBookingsByHospitalName(@RequestParam String hospitalName) {
        return bookingService.findByHospitalName(hospitalName).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Tìm kiếm chi tiết đặt chỗ theo thành phố
    @GetMapping("/city")
    public List<BookingDTO> getBookingsByCityName(@RequestParam String cityName) {
        return bookingService.findByCityName(cityName).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Tìm kiếm theo tên bệnh viện và thành phố
    @GetMapping("/search")
    public List<BookingDTO> getBookingsByHospitalAndCity(@RequestParam String hospitalName, @RequestParam String cityName) {
        return bookingService.findByHospitalNameAndCityName(hospitalName, cityName).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Tìm kiếm tất cả đặt chỗ liên quan đến một từ khóa (search)
    @GetMapping("/keyword")
    public List<BookingDTO> searchBookings(@RequestParam String keyword) {
        return bookingService.searchBookings(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy chi tiết đặt chỗ theo ID
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Integer id) {
        Optional<Booking> bookingOpt = bookingService.findBookingById(id);
        if (bookingOpt.isPresent()) {
            return ResponseEntity.ok(convertToDTO(bookingOpt.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Cập nhật thông tin đặt chỗ
    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Integer id, @RequestBody Booking updatedBooking) {
        Booking booking = bookingService.updateBooking(id, updatedBooking);
        if (booking != null) {
            return ResponseEntity.ok(convertToDTO(booking));
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

    @PostMapping("/calculate-cost")
    public ResponseEntity<Map<String, Object>> calculateCost(@RequestBody Map<String, Object> locationData) {
        double startLatitude = (double) locationData.get("startLatitude");
        double startLongitude = (double) locationData.get("startLongitude");
        double destinationLatitude = (double) locationData.get("destinationLatitude");
        double destinationLongitude = (double) locationData.get("destinationLongitude");

        double distance = calculateDistance(startLatitude, startLongitude, destinationLatitude, destinationLongitude);
        double costPerKmUSD = 1.5;
        double costInUSD = distance * costPerKmUSD;

        Map<String, Object> response = new HashMap<>();
        response.put("distance", distance);
        response.put("costInUSD", costInUSD);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-driver/{id}")
    public ResponseEntity<BookingDTO> updateBookingWithDriverId(@PathVariable Integer id, @RequestBody Map<String, Object> requestData) {
        Integer driverId = (Integer) requestData.get("driverId");

        Optional<Booking> bookingOptional = bookingService.findBookingById(id);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();

            Optional<Driver> driverOptional = driverService.findDriverById(driverId);
            if (driverOptional.isPresent()) {
                Driver driver = driverOptional.get();
                booking.setDriver(driver);
                Booking updatedBooking = bookingService.updateBooking(id, booking);
                return ResponseEntity.ok(convertToDTO(updatedBooking));
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private BookingDTO convertToDTO(Booking booking) {
        return new BookingDTO(
                booking.getBookingId(),
                booking.getPatient() != null ? booking.getPatient().getPatientId() : null,
                booking.getHospital() != null ? booking.getHospital().getHospitalId() : null,
                booking.getAmbulance() != null ? booking.getAmbulance().getAmbulanceId() : null,
                booking.getDriver() != null ? booking.getDriver().getDriverId() : null,
                booking.getLatitude(),
                booking.getLongitude(),
                booking.getDestinationLatitude(),
                booking.getDestinationLongitude(),
                booking.getBookingType(),
                booking.getPickupAddress(),
                booking.getPickupTime(),
                booking.getBookingStatus(),
                booking.getCreatedAt(),
                booking.getUpdatedAt(),
                booking.getCost(),
                booking.getAmbulanceType(),
                booking.getZipCode()
        );
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
