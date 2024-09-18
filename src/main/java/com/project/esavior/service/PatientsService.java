package com.project.esavior.service;

import com.project.esavior.model.Patients;
import com.project.esavior.repository.PatientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientsService {

    private final PatientsRepository patientsRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public PatientsService(PatientsRepository patientsRepository, BCryptPasswordEncoder passwordEncoder) {
        this.patientsRepository = patientsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Đăng ký bệnh nhân mới
    public Patients registerPatient(Patients patient) {
        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        return patientsRepository.save(patient);
    }

    // Xác thực đăng nhập
    public Patients authenticatePatient(String email, String password) {
        Patients patient = patientsRepository.findByEmail(email);
        if (patient != null && passwordEncoder.matches(password, patient.getPassword())) {
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
            // Nếu mật khẩu được cập nhật, mã hóa nó trước khi lưu
            if (updatedPatient.getPassword() != null && !updatedPatient.getPassword().isEmpty()) {
                patient.setPassword(passwordEncoder.encode(updatedPatient.getPassword()));
            }
            return patientsRepository.save(patient);
        }).orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + patientId));
    }
}
