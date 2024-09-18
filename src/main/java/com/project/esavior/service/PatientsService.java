package com.project.esavior.service;

import com.project.esavior.model.Patients;
import com.project.esavior.repository.PatientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientsService {

    private final PatientsRepository patientsRepository;

    @Autowired
    public PatientsService(PatientsRepository patientsRepository) {
        this.patientsRepository = patientsRepository;
    }

    // Đăng ký bệnh nhân mới
    public Patients registerPatient(Patients patient) {
        // Lưu mật khẩu trực tiếp mà không mã hóa
        return patientsRepository.save(patient);
    }

    // Xác thực đăng nhập
    public Patients authenticatePatient(String email, String password) {
        Patients patient = patientsRepository.findByEmail(email);
        // So sánh mật khẩu trực tiếp mà không mã hóa
        if (patient != null && patient.getPassword().equals(password)) {
            return patient; // Xác thực thành công
        }
        return null; // Xác thực thất bại
    }

    // Xem thông tin hồ sơ của bệnh nhân bằng ID
    public Optional<Patients> getPatientProfile(Integer patientId) {
        return patientsRepository.findById(patientId);
    }

    // Cập nhật thông tin hồ sơ của bệnh nhân
    public Patients updatePatientProfile(Integer patientId, Patients updatedPatient) {
        return patientsRepository.findById(patientId).map(patient -> {
            // Cập nhật thông tin
            patient.setPatientName(updatedPatient.getPatientName());
            patient.setPhoneNumber(updatedPatient.getPhoneNumber());
            patient.setAddress(updatedPatient.getAddress());
            patient.setEmergencyContact(updatedPatient.getEmergencyContact());
            // Nếu mật khẩu được cập nhật, lưu trực tiếp mà không mã hóa
            if (updatedPatient.getPassword() != null && !updatedPatient.getPassword().isEmpty()) {
                patient.setPassword(updatedPatient.getPassword());
            }
            return patientsRepository.save(patient);
        }).orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + patientId));
    }
}
