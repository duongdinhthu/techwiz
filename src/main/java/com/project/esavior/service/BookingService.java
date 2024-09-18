package com.project.esavior.service;

import com.project.esavior.model.Booking;
import com.project.esavior.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Tìm kiếm chi tiết đặt chỗ theo tên bệnh viện
    public List<Booking> findByHospitalName(String hospitalName) {
        return bookingRepository.findByHospital_HospitalName(hospitalName);
    }

    // Tìm kiếm chi tiết đặt chỗ theo thành phố
    public List<Booking> findByCityName(String cityName) {
        return bookingRepository.findByHospital_City_CityName(cityName);
    }

    // Tìm kiếm theo tên bệnh viện và thành phố
    public List<Booking> findByHospitalNameAndCityName(String hospitalName, String cityName) {
        return bookingRepository.findByHospital_HospitalNameAndHospital_City_CityName(hospitalName, cityName);
    }

    // Tìm kiếm tất cả đặt chỗ liên quan đến một từ khóa (search)
    public List<Booking> searchBookings(String keyword) {
        return bookingRepository.findByHospital_HospitalNameContainingOrHospital_City_CityNameContaining(keyword, keyword);
    }
}
