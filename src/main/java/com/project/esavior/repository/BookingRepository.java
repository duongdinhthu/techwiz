package com.project.esavior.repository;

import com.project.esavior.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByAmbulance_Hospital_City_CityNameAndAmbulance_Hospital_HospitalName(String cityName, String hospitalName);
}
