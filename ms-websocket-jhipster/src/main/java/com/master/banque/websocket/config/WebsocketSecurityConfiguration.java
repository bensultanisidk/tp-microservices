package com.master.banque.websocket.config;

import com.master.banque.websocket.security.AuthoritiesConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebsocketSecurityConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
            .nullDestMatcher()
            .permitAll()
            .simpDestMatchers("/topic/tracker")
            .permitAll()
            // AUTORISE l'accès aux topics de notification sans authentification
            .simpDestMatchers("/topic/notification")
            .permitAll()
            .simpDestMatchers("/topic/notifications")
            .permitAll()
            .simpDestMatchers("/topic/broadcast")
            .permitAll()
            .simpDestMatchers("/topic/chat")
            .permitAll()
            // matches any destination that starts with /topic/
            .simpDestMatchers("/topic/**")
            .permitAll() // ← CHANGE de .authenticated() à .permitAll()
            // message types other than MESSAGE and SUBSCRIBE
            .simpTypeMatchers(SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE)
            .permitAll() // ← CHANGE de .denyAll() à .permitAll()
            // catch all
            .anyMessage()
            .permitAll(); // ← CHANGE de .denyAll() à .permitAll()
    }

    /**
     * Désactive la sécurité CSRF pour WebSocket (nécessaire pour SockJS)
     */
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}