package com.project.esavior.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer bookingId;

    @ManyToOne
    @JoinColumn(name = "ambulance_id")
    @JsonBackReference // Hoặc sử dụng @JsonIgnore để bỏ qua
    private Ambulance ambulance;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonBackReference // Hoặc sử dụng @JsonIgnore để bỏ qua
    private Patients patient;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    @JsonBackReference // Hoặc sử dụng @JsonIgnore để bỏ qua
    private Hospital hospital;

    @Column(name = "booking_type")
    private String bookingType;

    @Column(name = "pickup_address")
    private String pickupAddress;

    @Column(name = "pickup_time")
    private LocalDateTime pickupTime;

    @Column(name = "booking_status")
    private String bookingStatus;

    @Column(name = "booking_created_at")
    private LocalDateTime createdAt;

    @Column(name = "booking_updated_at")
    private LocalDateTime updatedAt;

    // Constructors, Getters và Setters
    public Booking() {}

    // Getters and Setters for each field

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Ambulance getAmbulance() {
        return ambulance;
    }

    public void setAmbulance(Ambulance ambulance) {
        this.ambulance = ambulance;
    }

    public Patients getPatient() {
        return patient;
    }

    public void setPatient(Patients patient) {
        this.patient = patient;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
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
}
