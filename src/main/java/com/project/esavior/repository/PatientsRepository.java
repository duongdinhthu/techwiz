package com.project.esavior.repository;

import com.project.esavior.model.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientsRepository extends JpaRepository<Patients, Integer> {
    // Các phương thức truy vấn tùy chỉnh nếu cần, ví dụ:
}
