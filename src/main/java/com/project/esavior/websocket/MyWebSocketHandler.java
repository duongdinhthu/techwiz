package com.project.esavior.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;

import java.util.concurrent.ConcurrentHashMap;



public class MyWebSocketHandler extends TextWebSocketHandler {
    private final ConcurrentHashMap<Integer, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Integer driverId = Integer.parseInt(session.getUri().getQuery().split("=")[1]);
        sessions.put(driverId, session);
        System.out.println("WebSocket connected for driverId: " + driverId);
    }

    public void sendMessageToDriver(Integer driverId, String message) {
        WebSocketSession session = sessions.get(driverId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Driver not connected: " + driverId);
        }
    }
}
