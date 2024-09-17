package com.project.esavior.service;

import com.project.esavior.model.Patients;
import com.project.esavior.repository.PatientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientsService {
    @Autowired
    private PatientsRepository patientsRepository;

    // Quản lý hồ sơ người dùng
    public Patients updateProfile(Integer patientId, Patients patientDetails) {
        Patients patient = patientsRepository.findById(patientId).orElse(null);
        if (patient != null) {
            patient.setPatientName(patientDetails.getPatientName());
            patient.setPatientAddress(patientDetails.getPatientAddress());
            // Cập nhật các thông tin khác
            return patientsRepository.save(patient);
        }
        return null;
    }
}
