package com.project.esavior.service;

import com.project.esavior.model.Patients;
import com.project.esavior.repository.PatientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PatientsService {

    @Autowired
    private PatientsRepository patientsRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Đăng ký bệnh nhân mới
    public Patients registerPatient(Patients patient) {
        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        return patientsRepository.save(patient);
    }

    // Xác thực đăng nhập
    public Patients authenticatePatient(String email, String password) {
        Patients patient = patientsRepository.findByPatientEmail(email);
        if (patient != null && passwordEncoder.matches(password, patient.getPassword())) {
            return patient; // Xác thực thành công
        }
        return null; // Xác thực thất bại
    }
}
