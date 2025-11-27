package com.master.banque.websocket.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final Random random = new Random();
    private final List<String> notificationTypes = Arrays.asList(
        "💰 nouvelle commande", 
        "🚚 nouvelle livraison", 
        "⚠️ nouvelle réclamation",
        "💳 paiement reçu",
        "🔄 transfert effectué"
    );

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedDelay = 8000)
    public void sendRandomNotification() {
        String notificationType = notificationTypes.get(random.nextInt(notificationTypes.size()));
        String message = notificationType + " #" + (random.nextInt(1000) + 1);
        
        System.out.println("🎯 WebSocket - Notification envoyée: " + message);
        
        // ENVOYER SUR TOUS LES TOPICS POUR ÊTRE SÛR
        messagingTemplate.convertAndSend("/topic/notification", message);
        messagingTemplate.convertAndSend("/topic/notifications", message);
        
        System.out.println("✅ Envoyé sur /topic/notification et /topic/notifications");
    }
}