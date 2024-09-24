package com.project.esavior.model;
import java.io.Serializable;
import java.util.List;  // Import List
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "driverlocation")
public class DriverLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Integer driverId;

    @Column(name = "latitude")
    private Double latitude; // Thêm trường này

    @Column(name = "longitude")
    private Double longitude;

    public DriverLocation() {}

    public DriverLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
