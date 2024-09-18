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

    private final DriverRepository driverRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository, BookingRepository bookingRepository) {
        this.driverRepository = driverRepository;
        this.bookingRepository = bookingRepository;
    }

    // Quản lý tình trạng xe cứu thương
    public Driver updateDriverStatus(Integer  driverId, String status) {
        // Kiểm tra trạng thái đầu vào
        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("Invalid status");
        }

        return driverRepository.findById(driverId)
                .map(driver -> {
                    driver.setStatus(status);
                    return driverRepository.save(driver);
                })
                .orElseThrow(() -> new IllegalArgumentException("Driver not found with ID: " + driverId));
    }

    // Cập nhật trạng thái đặt chỗ
    public Booking updateBookingStatus(Integer bookingId, String status) {
        // Kiểm tra trạng thái đầu vào
        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("Invalid status");
        }

        return bookingRepository.findById(bookingId)
                .map(booking -> {
                    booking.setBookingStatus(status);
                    return bookingRepository.save(booking);
                })
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + bookingId));
    }

    // Kiểm tra xem trạng thái có hợp lệ hay không
    private boolean isValidStatus(String status) {
        // Thêm các trạng thái hợp lệ vào danh sách này
        return status.equalsIgnoreCase("Pending") ||
                status.equalsIgnoreCase("Accepted") ||
                status.equalsIgnoreCase("Completed") ||
                status.equalsIgnoreCase("Waiting List");
    }
}
