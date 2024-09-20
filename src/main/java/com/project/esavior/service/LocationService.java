package com.project.esavior.service;

import com.project.esavior.model.Location;
import com.project.esavior.model.LocationRequest;
import com.project.esavior.model.NearestDriverRequest;
import com.project.esavior.model.NearestDriverResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    private List<Location> driverLocations = new ArrayList<>(); // Giả lập lưu trữ vị trí tài xế

    // Cập nhật vị trí của tài xế/khách hàng
    public boolean updateLocation(LocationRequest request) {
        // Lưu vị trí mới hoặc cập nhật vị trí của tài xế/khách hàng trong danh sách
        driverLocations.removeIf(loc -> loc.getUserId().equals(request.getUserId()) && loc.getRole().equals(request.getRole()));
        driverLocations.add(new Location(request.getUserId(), request.getRole(), request.getLatitude(), request.getLongitude(), LocalDateTime.now()));
        return true; // Trả về thành công
    }

    // Lấy vị trí của tài xế/khách hàng
    public Location getLocation(String userId, String role) {
        // Lấy vị trí của tài xế hoặc khách hàng từ danh sách
        return driverLocations.stream()
                .filter(loc -> loc.getUserId().equals(userId) && loc.getRole().equals(role))
                .findFirst()
                .orElse(null); // Trả về null nếu không tìm thấy
    }

    // Tìm tài xế gần nhất cho khách hàng dựa trên vị trí
    public NearestDriverResponse findNearestDriver(NearestDriverRequest request) {
        Location nearestDriver = null;
        double minDistance = Double.MAX_VALUE;

        // Tìm tài xế gần nhất bằng cách tính khoảng cách
        for (Location driverLocation : driverLocations) {
            if (driverLocation.getRole().equals("driver")) {
                double distance = calculateDistance(
                        request.getCustomerLatitude(),
                        request.getCustomerLongitude(),
                        driverLocation.getLatitude(),
                        driverLocation.getLongitude()
                );
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestDriver = driverLocation;
                }
            }
        }

        if (nearestDriver != null) {
            return new NearestDriverResponse(nearestDriver.getUserId(), nearestDriver.getLatitude(), nearestDriver.getLongitude(), minDistance + " meters");
        } else {
            return null; // Không tìm thấy tài xế nào
        }
    }

    // Công thức tính khoảng cách (Haversine formula)
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Bán kính Trái Đất tính bằng km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1000; // Đổi sang mét
    }
}
