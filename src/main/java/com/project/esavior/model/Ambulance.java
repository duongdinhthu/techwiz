package com.project.esavior.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ambulances")
public class Ambulance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ambulance_id")
    private Integer ambulanceId;

    @Column(name = "ambulance_number", nullable = false, unique = true)
    private String ambulanceNumber;

    @OneToOne
    @JoinColumn(name = "driver_id")
    @JsonIgnore
    private Driver driver;

    @Column(name = "ambulance_status", nullable = false)
    private String ambulanceStatus;

    @Column(name = "ambulance_type", nullable = false)
    private String ambulanceType;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    @JsonIgnore     private Hospital hospital;
    @Column(name = "ambulance_created_at")
    private LocalDateTime createdAt;

    @Column(name = "ambulance_updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "ambulance")
    private List<Booking> bookings;

    // Constructors, Getters và Setters
    public Ambulance() {}

    public Integer getAmbulanceId() {
        return ambulanceId;
    }

    public void setAmbulanceId(Integer ambulanceId) {
        this.ambulanceId = ambulanceId;
    }

    public String getAmbulanceNumber() {
        return ambulanceNumber;
    }

    public void setAmbulanceNumber(String ambulanceNumber) {
        this.ambulanceNumber = ambulanceNumber;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getAmbulanceStatus() {
        return ambulanceStatus;
    }

    public void setAmbulanceStatus(String ambulanceStatus) {
        this.ambulanceStatus = ambulanceStatus;
    }

    public String getAmbulanceType() {
        return ambulanceType;
    }

    public void setAmbulanceType(String ambulanceType) {
        this.ambulanceType = ambulanceType;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }


    // Getters and Setters for each field
}
