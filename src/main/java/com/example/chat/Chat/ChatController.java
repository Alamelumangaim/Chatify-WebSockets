package com.example.chat.Chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
@Controller
public class ChatController {
    @MessageMapping("chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage message){
        return message;
    }
    @MessageMapping("chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage message,SimpMessageHeaderAccessor simpleMessageHeaderAccessor){
        simpleMessageHeaderAccessor.getSessionAttributes().put("username",message.getSender());
        System.out.println(message.getMessageType());
        return message;
    }
}
