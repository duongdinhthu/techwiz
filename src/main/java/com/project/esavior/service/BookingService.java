package com.project.esavior.service;

import com.project.esavior.model.Booking;
import com.project.esavior.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    // Đặt xe cứu thương
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> findBookingsByCityAndHospital(String cityName, String hospitalName) {
        return bookingRepository.findByAmbulance_Hospital_City_CityNameAndAmbulance_Hospital_HospitalName(cityName, hospitalName);
    }

}
