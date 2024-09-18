package com.project.esavior.controller;

import com.project.esavior.model.Booking;
import com.project.esavior.model.Driver;
import com.project.esavior.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    @Autowired
    private DriverService driverService;

    // Quản lý tình trạng xe cứu thương
    @PutMapping("/{driverId}/status")
    public Driver updateDriverStatus(@PathVariable Integer driverId, @RequestParam String status) {
        return driverService.updateDriverStatus(driverId, status);
    }

    // Cập nhật trạng thái đặt chỗ
    @PutMapping("/bookings/{bookingId}/status")
    public Booking updateBookingStatus(@PathVariable Integer bookingId, @RequestParam String status) {
        return driverService.updateBookingStatus(bookingId, status);
    }
}
