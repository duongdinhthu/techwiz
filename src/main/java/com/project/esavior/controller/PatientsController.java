package com.project.esavior.controller;

import com.project.esavior.model.Patients;
import com.project.esavior.service.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
public class PatientsController {
    @Autowired
    private PatientsService patientsService;

    // Quản lý hồ sơ người dùng
    @PutMapping("/{patientId}")
    public Patients updateProfile(@PathVariable Integer patientId, @RequestBody Patients patientDetails) {
        return patientsService.updateProfile(patientId, patientDetails);
    }
}
