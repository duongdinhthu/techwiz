package com.project.esavior.model;

public class NearestDriverRequest {
    private double customerLatitude;
    private double customerLongitude;

    // Constructor
    public NearestDriverRequest(double customerLatitude, double customerLongitude) {
        this.customerLatitude = customerLatitude;
        this.customerLongitude = customerLongitude;
    }

    // Getters và Setters
    public double getCustomerLatitude() {
        return customerLatitude;
    }

    public void setCustomerLatitude(double customerLatitude) {
        this.customerLatitude = customerLatitude;
    }

    public double getCustomerLongitude() {
        return customerLongitude;
    }

    public void setCustomerLongitude(double customerLongitude) {
        this.customerLongitude = customerLongitude;
    }
}
