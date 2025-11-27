package com.master.banque.websocket.web.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final CopyOnWriteArrayList<String> activeUsers = new CopyOnWriteArrayList<>();

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Endpoints REST simples
    @GetMapping("/")
    @ResponseBody
    public String helloWorld() {
        return "{\"message\": \"Hello World - WebSocket Service\"}";
    }

    @GetMapping("/test-notification")
    @ResponseBody
    public String testNotification() {
        String testMessage = "🔔 TEST MANUEL - Notification #" + System.currentTimeMillis();
        
        // Test manuel sur tous les topics
        messagingTemplate.convertAndSend("/topic/notification", testMessage);
        messagingTemplate.convertAndSend("/topic/notifications", testMessage);
        messagingTemplate.convertAndSend("/topic/broadcast", testMessage);
        
        System.out.println("🎯 TEST MANUEL - Notification envoyée: " + testMessage);
        return "{\"status\": \"Notification de test envoyée sur tous les topics\"}";
    }

    // WebSocket - Echo
    @MessageMapping("/hello")
    @SendTo("/topic/echo")
    public String handleHelloMessage(String message) {
        System.out.println("🎯 WebSocket - Message reçu: " + message);
        return "Echo: " + message;
    }

    // WebSocket - Chat broadcast
    @MessageMapping("/chat")
    @SendTo("/topic/broadcast")
    public String handleChatMessage(String message) {
        System.out.println("🎯 WebSocket - Chat message: " + message);
        return "CHAT: " + message;
    }

    // WebSocket - Envoi direct de notification
    @MessageMapping("/send-notification")
    @SendTo("/topic/notification")
    public String handleNotificationMessage(String message) {
        System.out.println("🎯 WebSocket - Notification personnalisée: " + message);
        return "NOTIFICATION: " + message;
    }
}