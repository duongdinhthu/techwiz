package com.project.esavior.repository;

import com.project.esavior.model.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientsRepository extends JpaRepository<Patients, Integer> {
    // Phương thức để tìm bệnh nhân theo email (sử dụng cho đăng nhập)
    Patients findByPatientEmail(String email);
}
