package com.project.esavior.controller;

import com.project.esavior.model.Ambulance;
import com.project.esavior.model.Booking;
import com.project.esavior.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    // Quản lý xe cứu thương
    @PostMapping("/ambulances")
    public Ambulance createOrUpdateAmbulance(@RequestBody Ambulance ambulance) {
        return adminService.createOrUpdateAmbulance(ambulance);
    }

    @GetMapping("/ambulances")
    public List<Ambulance> getAllAmbulances() {
        return adminService.getAllAmbulances();
    }

    // Xem danh sách đặt chỗ
    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        return adminService.getAllBookings();
    }
}
