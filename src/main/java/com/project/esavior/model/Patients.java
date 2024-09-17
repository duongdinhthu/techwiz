package com.project.esavior.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "patients") // Tên bảng trong cơ sở dữ liệu
public class Patients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Integer patientId;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "patient_dob")
    private Date patientDob;

    @Column(name = "patient_email")
    private String patientEmail;

    @Column(name = "patient_phone")
    private Integer patientPhone;

    @Column(name = "patient_address")
    private String patientAddress;

    @Column(name = "patient_password")
    private String patientPassword;

    @Column(name = "patient_username")
    private String patientUsername;

    @Column(name = "patient_gender")
    private String patientGender;

    @Column(name = "patient_code")
    private String patientCode;

    @Column(name = "patient_img")
    private String patientImg; // Lưu trữ đường dẫn ảnh

    // Constructors
    public Patients() {
    }

    // Getters và Setters
    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Date getPatientDob() {
        return patientDob;
    }

    public void setPatientDob(Date patientDob) {
        this.patientDob = patientDob;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public Integer getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(Integer patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientPassword() {
        return patientPassword;
    }

    public void setPatientPassword(String patientPassword) {
        this.patientPassword = patientPassword;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public void setPatientUsername(String patientUsername) {
        this.patientUsername = patientUsername;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getPatientImg() {
        return patientImg;
    }

    public void setPatientImg(String patientImg) {
        this.patientImg = patientImg;
    }

    @Override
    public String toString() {
        return "Patients{" +
                "patientId=" + patientId +
                ", patientName='" + patientName + '\'' +
                ", patientDob=" + patientDob +
                ", patientEmail='" + patientEmail + '\'' +
                ", patientPhone=" + patientPhone +
                ", patientAddress='" + patientAddress + '\'' +
                ", patientPassword='" + patientPassword + '\'' +
                ", patientUsername='" + patientUsername + '\'' +
                ", patientGender='" + patientGender + '\'' +
                ", patientCode='" + patientCode + '\'' +
                ", patientImg='" + patientImg + '\'' +
                '}';
    }
}
