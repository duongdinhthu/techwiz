package com.project.esavior.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ambulances") // Tên bảng trong cơ sở dữ liệu
public class Ambulance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ambulance_id")
    private Integer ambulanceId;

    @Column(name = "ambulance_number")
    private String ambulanceNumber;

    @Column(name = "driver_id")
    private Integer driverId;

    @Column(name = "ambulance_status")
    private String ambulanceStatus;

    @Column(name = "ambulance_type")
    private String ambulanceType;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Column(name = "ambulance_create_at")
    private LocalDateTime createAt;

    @Column(name = "ambulance_update_at")
    private LocalDateTime updateAt;

    // Constructors
    public Ambulance() {
    }

    public String getAmbulanceType() {
        return ambulanceType;
    }

    public void setAmbulanceType(String ambulanceType) {
        this.ambulanceType = ambulanceType;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    // Getters và Setters
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

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getAmbulanceStatus() {
        return ambulanceStatus;
    }

    public void setAmbulanceStatus(String ambulanceStatus) {
        this.ambulanceStatus = ambulanceStatus;
    }

    @Override
    public String toString() {
        return "Ambulance{" +
                "ambulanceId=" + ambulanceId +
                ", ambulanceNumber='" + ambulanceNumber + '\'' +
                ", driverId=" + driverId +
                ", ambulanceStatus='" + ambulanceStatus + '\'' +
                '}';
    }
}
