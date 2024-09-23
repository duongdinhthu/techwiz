package com.project.esavior.service;

import com.project.esavior.model.Location;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LocationService {
    private double latitude;
    private double longitude;
    private String customerName;
    private String phoneNumber;
    private String email;
    private Integer driverId; // Lưu driverId cho tài xế
    private double destinationLatitude;  // Vĩ độ điểm đến
    private double destinationLongitude;
    private String bookingStatus;
    // Cập nhật vị trí và thông tin khách hàng bao gồm điểm đến
    public void updateLocationAndCustomerInfo(Location location, String customerName, String phoneNumber, String email, double destinationLatitude, double destinationLongitude,String bookingStatus) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
        this.bookingStatus = bookingStatus;
    }
    public void updateLocation(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        // Bạn có thể thêm các thao tác khác tại đây nếu cần
    }

    // Các getter và setter khác
    public Map<String, Object> getCustomerAndLocationInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("latitude", latitude);
        info.put("longitude", longitude);
        info.put("destinationLatitude", destinationLatitude);
        info.put("destinationLongitude", destinationLongitude);
        info.put("customerName", customerName);
        info.put("phoneNumber", phoneNumber);
        info.put("email", email);
        return info;
    }

    public void updateDriverId(Integer driverId) {
        this.driverId = driverId;
    }
    public void updateDriverLocation(int driverId, Location location) {
        driverLocations.put(driverId, location);
        System.out.println("Driver location updated in service for driverId: " + driverId);
    }

    public Location getDriverLocation(int driverId) {
        return driverLocations.get(driverId);
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Integer getDriverId() {
        return this.driverId;
    }
    private Map<Integer, Location> driverLocations = new HashMap<>();

    // Cập nhật vị trí của tài xế

}
