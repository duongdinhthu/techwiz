package com.project.esavior.controller;

import com.project.esavior.model.Booking;
import com.project.esavior.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    // Đặt xe cứu thương
    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // Tìm kiếm theo thành phố hoặc bệnh viện
    @GetMapping("/search")
    public List<Booking> findBookingsByCityAndHospital(@RequestParam String cityName, @RequestParam String hospitalName) {
        return bookingService.findBookingsByCityAndHospital(cityName, hospitalName);
    }

}
