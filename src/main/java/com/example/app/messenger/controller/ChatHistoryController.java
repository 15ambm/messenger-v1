package com.example.app.messenger.controller;

import com.example.app.messenger.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatHistoryController {
    private static final Logger logger = LoggerFactory.getLogger(ChatHistoryController.class);
    private MessageController messageController;
    public ChatHistoryController(MessageController messageController) {
        this.messageController = messageController;
    }
    @GetMapping("/chat/history")
    public List<Message> getChatHistory() {
        logger.info("Chat history requested");
        return messageController.getMessageHistory();
    }
}
