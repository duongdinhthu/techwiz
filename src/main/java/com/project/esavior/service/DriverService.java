package com.project.esavior.service;

import com.project.esavior.model.Booking;
import com.project.esavior.model.Driver;
import com.project.esavior.repository.BookingRepository;
import com.project.esavior.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private BookingRepository bookingRepository;

    // Quản lý tình trạng xe cứu thương
    public Driver updateDriverStatus(Long driverId, String status) {
        Driver driver = driverRepository.findById(driverId).orElse(null);
        if (driver != null) {
            driver.setStatus(status);
            return driverRepository.save(driver);
        }
        return null;
    }

    // Cập nhật trạng thái đặt chỗ
    public Booking updateBookingStatus(Integer bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking != null) {
            booking.setBookingStatus(status);
            return bookingRepository.save(booking);
        }
        return null;
    }

}
