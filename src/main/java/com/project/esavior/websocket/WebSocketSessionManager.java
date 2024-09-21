package com.project.esavior.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {

    // Dùng ConcurrentHashMap để lưu trữ các session theo driverId
    private final Map<Integer, WebSocketSession> sessions = new ConcurrentHashMap<>();

    // Phương thức để thêm session mới
    public void addSession(Integer driverId, WebSocketSession session) {
        sessions.put(driverId, session);
    }

    // Phương thức để xóa session khi tài xế ngắt kết nối
    public void removeSession(Integer driverId) {
        sessions.remove(driverId);
    }

    // Phương thức để lấy session theo driverId
    public WebSocketSession getSession(Integer driverId) {
        return sessions.get(driverId);
    }
}
