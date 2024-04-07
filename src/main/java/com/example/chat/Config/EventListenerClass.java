package com.example.chat.Config;

import com.example.chat.Chat.ChatMessage;
import com.example.chat.Chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
@Component
@RequiredArgsConstructor
@Slf4j
public class EventListenerClass {
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    @EventListener
    public void handleWebSocketListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username!=null){
            log.info("User disconnected : {}",username);
            var message = ChatMessage.builder()
                    .messageType(MessageType.LEAVE)
                    .sender(username)
                    .build();
            simpMessageSendingOperations.convertAndSend("/topic/public",message);
        }
    }
}
