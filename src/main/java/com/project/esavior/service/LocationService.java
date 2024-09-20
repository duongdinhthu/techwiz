package com.project.esavior.service;

import com.project.esavior.model.Location;
import com.project.esavior.model.LocationRequest;
import com.project.esavior.model.NearestDriverRequest;
import com.project.esavior.model.NearestDriverResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LocationService {

    // Cập nhật vị trí của khách hàng/tài xế
    public boolean updateLocation(LocationRequest request) {
        // Lưu vị trí của tài xế/khách hàng vào cơ sở dữ liệu hoặc sử dụng Firebase nếu muốn.
        // Ở đây, chúng ta giả sử lưu vào bộ nhớ hoặc một nơi nào đó tạm thời.

        // Giả sử sử dụng Firebase hoặc MySQL
        System.out.println("Cập nhật vị trí cho userId: " + request.getUserId());
        return true; // Trả về thành công
    }


    // Lấy vị trí của khách hàng/tài xế
    public Location getLocation(String userId, String role) {
        // Logic lấy vị trí từ cơ sở dữ liệu
        // Nếu dùng Firebase, sử dụng Firebase SDK để lấy thông tin
        return new Location(userId, role, 21.0285, 105.8542, LocalDateTime.now());
    }

    // Tìm tài xế gần nhất
    public NearestDriverResponse findNearestDriver(NearestDriverRequest request) {
        // Tính toán khoảng cách giữa khách hàng và tài xế
        // Có thể dùng Haversine formula hoặc thư viện để tính khoảng cách
        // Ở đây mình giả định trả về tài xế gần nhất với khoảng cách tính toán tạm thời
        String driverId = "driver123"; // Tài xế gần nhất (giả lập)
        double latitude = 21.0290; // Vị trí giả lập của tài xế
        double longitude = 105.8550; // Vị trí giả lập của tài xế
        String distance = "500 meters"; // Khoảng cách giả lập

        // Trả về thông tin tài xế gần nhất
        return new NearestDriverResponse(driverId, latitude, longitude, distance);
    }

}
