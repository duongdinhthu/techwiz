package com.project.esavior.controller;

import com.project.esavior.model.Patients;
import com.project.esavior.service.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/patients")
public class PatientsController {

    private final PatientsService patientsService;

    @Autowired
    public PatientsController(PatientsService patientsService) {
        this.patientsService = patientsService;
    }

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
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("patientId", authenticatedPatient.getPatientId());
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }

    // Xem thông tin hồ sơ bệnh nhân
    @GetMapping("/profile/{id}")
    public ResponseEntity<Patients> getPatientProfile(@PathVariable Integer id) {
        return patientsService.getPatientProfile(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Cập nhật thông tin hồ sơ bệnh nhân
    @PutMapping("/profile/{id}")
    public ResponseEntity<Patients> updatePatientProfile(@PathVariable Integer id, @RequestBody Patients updatedPatient) {
        try {
            Patients updatedProfile = patientsService.updatePatientProfile(id, updatedPatient);
            return ResponseEntity.ok(updatedProfile);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
