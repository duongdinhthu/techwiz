package com.project.esavior.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bookings") // Tên bảng trong cơ sở dữ liệu
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer bookingId;

    @Column(name = "booking_date")
    private Date bookingDate;

    @Column(name = "patient_id")
    private Integer patientId;

    @Column(name = "booking_status")
    private String bookingStatus;

    @Column(name = "booking_address")
    private String bookingAddress;

    @Column(name = "booking_details")
    private String bookingDetails;
    @ManyToOne
    @JoinColumn(name = "ambulance_id")
    private Ambulance ambulance;
    // Constructors
    public Booking() {
    }

    public Ambulance getAmbulance() {
        return ambulance;
    }

    public void setAmbulance(Ambulance ambulance) {
        this.ambulance = ambulance;
    }

    // Getters và Setters
    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }



    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getBookingAddress() {
        return bookingAddress;
    }

    public void setBookingAddress(String bookingAddress) {
        this.bookingAddress = bookingAddress;
    }

    public String getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(String bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", bookingDate=" + bookingDate +
                ", patientId=" + patientId +
                ", bookingStatus='" + bookingStatus + '\'' +
                ", bookingAddress='" + bookingAddress + '\'' +
                ", bookingDetails='" + bookingDetails + '\'' +
                '}';
    }
}
