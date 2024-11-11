package com.example.app.messenger.controller;

import com.example.app.messenger.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    private final List<Message> messageHistory = new ArrayList<>();

    @MessageMapping("/chat/message")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message) {
        logger.info("Received message from {}: {}", message.getSender(), message.getContent());
        messageHistory.add(message);
        return message;
    }

    public List<Message> getMessageHistory() {
        return messageHistory;
    }


}
