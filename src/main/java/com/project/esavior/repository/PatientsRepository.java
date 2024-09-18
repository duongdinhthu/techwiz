package com.project.esavior.repository;

import com.project.esavior.model.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientsRepository extends JpaRepository<Patients, Integer> {
    // Đảm bảo tên phương thức khớp với tên trường trong class Patients
    Patients findByEmail(String email);
}
