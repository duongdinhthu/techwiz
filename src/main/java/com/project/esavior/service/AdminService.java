package com.project.esavior.service;

import com.project.esavior.model.Ambulance;
import com.project.esavior.model.Booking;
import com.project.esavior.repository.AmbulanceRepository;
import com.project.esavior.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AmbulanceRepository ambulanceRepository;

    @Autowired
    private BookingRepository bookingRepository;

    // Quản lý xe cứu thương
    public Ambulance createOrUpdateAmbulance(Ambulance ambulance) {
        return ambulanceRepository.save(ambulance);
    }

    public List<Ambulance> getAllAmbulances() {
        return ambulanceRepository.findAll();
    }

    // Xem chi tiết đặt chỗ
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();

    }
}

