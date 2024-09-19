package com.project.esavior.controller;

import com.project.esavior.model.Patients;
import com.project.esavior.service.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    // Kiểm tra email bệnh nhân
    @PostMapping("/check")
    public ResponseEntity<Boolean> checkPatient(@RequestBody Patients patient) {
        System.out.println("Checking email: " + patient.getEmail());
        Optional<Patients> existingPatient = patientsService.findByEmail(patient.getEmail());
        if (existingPatient.isPresent()) {
            System.out.println("Email found: " + existingPatient.get().getEmail());
            return new ResponseEntity<>(true, HttpStatus.OK); // Email đã tồn tại
        }
        System.out.println("Email not found");
        return new ResponseEntity<>(false, HttpStatus.OK); // Email chưa tồn tại
    }


    // Cập nhật thông tin bệnh nhân nếu email đã tồn tại
    @PutMapping("/update")
    public ResponseEntity<String> updatePatient(@RequestBody Patients patient) {
        Optional<Patients> existingPatient = patientsService.findByEmail(patient.getEmail());
        if (existingPatient.isPresent()) {
            Patients updatePatient = existingPatient.get();
            updatePatient.setPatientName(patient.getPatientName());
            updatePatient.setPhoneNumber(patient.getPhoneNumber());
            patientsService.save(updatePatient);
            return new ResponseEntity<>("Cập nhật thông tin thành công", HttpStatus.OK);
        }
        return new ResponseEntity<>("Bệnh nhân không tồn tại", HttpStatus.NOT_FOUND);
    }
}
