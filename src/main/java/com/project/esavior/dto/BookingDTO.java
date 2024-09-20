package com.project.esavior.dto;

import java.time.LocalDateTime;

public class BookingDTO {
    private Integer bookingId;
    private String email; // Chứa email của bệnh nhân để tìm kiếm
    private String bookingType;
    private String pickupAddress;
    private LocalDateTime pickupTime;
    private String bookingStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Double cost;

    public Integer getBookingId() {
        return bookingId;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
// Getters và Setters
    // ...
}
