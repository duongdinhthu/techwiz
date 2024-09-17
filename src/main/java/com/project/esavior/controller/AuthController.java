package com.project.esavior.controller;

import com.project.esavior.model.Patients;
import com.project.esavior.service.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private PatientsService patientsService;



    @PostMapping("/login")
    public String login(@RequestBody Patients patient) {
        // Implement logic for login and JWT token generation if needed
        return "Login successful"; // Thay thế bằng logic đăng nhập thực tế
    }
}
