package com.project.esavior.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class MyWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private WebSocketSessionManager sessionManager;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Giả sử bạn có cách lấy driverId từ URI hoặc từ session
        Integer driverId = getDriverIdFromSession(session);
        sessionManager.addSession(driverId, session);
        System.out.println("New WebSocket connection established for driverId: " + driverId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Message received: " + message.getPayload());
        session.sendMessage(new TextMessage("Message received: " + message.getPayload()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Integer driverId = getDriverIdFromSession(session);
        sessionManager.removeSession(driverId);
        System.out.println("WebSocket connection closed for driverId: " + driverId);
    }

    private Integer getDriverIdFromSession(WebSocketSession session) {
        // Giả sử bạn lấy driverId từ session attributes hoặc query string
        // Đây chỉ là ví dụ, bạn sẽ cần thay đổi cho phù hợp
        return (Integer) session.getAttributes().get("driverId");
    }
}
