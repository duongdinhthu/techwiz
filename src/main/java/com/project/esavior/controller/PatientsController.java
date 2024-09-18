package com.project.esavior.controller;

import com.project.esavior.model.Patients;
import com.project.esavior.service.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
public class PatientsController {

    @Autowired
    private PatientsService patientsService;

    // Đăng ký bệnh nhân mới
    @PostMapping("/register")
    public ResponseEntity<Patients> registerPatient(@RequestBody Patients patient) {
        Patients registeredPatient = patientsService.registerPatient(patient);
        return new ResponseEntity<>(registeredPatient, HttpStatus.CREATED);
    }

    // Đăng nhập bệnh nhân
    @PostMapping("/login")
    public ResponseEntity<String> loginPatient(@RequestParam String email, @RequestParam String password) {
        Patients authenticatedPatient = patientsService.authenticatePatient(email, password);
        if (authenticatedPatient != null) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }
}
