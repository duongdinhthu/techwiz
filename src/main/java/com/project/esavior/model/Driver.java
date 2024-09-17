package com.project.esavior.model;

import jakarta.persistence.*;

@Entity
@Table(name = "drivers") // Tên bảng trong cơ sở dữ liệu
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Long driverId;

    @Column(name = "driver_name")
    private String driverName;

    @Column(name = "driver_license_number")
    private String driverLicenseNumber;

    @Column(name = "driver_phone")
    private String driverPhone;

    @Column(name = "driver_address")
    private String driverAddress;
    @Column(name = "email")  // Thêm cột email
    private String email;

    @Column(name = "status")  // Thêm cột email
    private String status;

    // Constructors
    public Driver() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getters và Setters
    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }

    public void setDriverLicenseNumber(String driverLicenseNumber) {
        this.driverLicenseNumber = driverLicenseNumber;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverAddress() {
        return driverAddress;
    }

    public void setDriverAddress(String driverAddress) {
        this.driverAddress = driverAddress;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driverId=" + driverId +
                ", driverName='" + driverName + '\'' +
                ", driverLicenseNumber='" + driverLicenseNumber + '\'' +
                ", driverPhone='" + driverPhone + '\'' +
                ", driverAddress='" + driverAddress + '\'' +
                '}';
    }
}
