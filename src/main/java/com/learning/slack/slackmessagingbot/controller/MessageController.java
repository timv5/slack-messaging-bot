package com.learning.slack.slackmessagingbot.controller;

import com.learning.slack.slackmessagingbot.dto.MessageRequest;
import com.learning.slack.slackmessagingbot.service.impl.MessageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/message")
@RestController
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    private final MessageServiceImpl messageService;

    @Autowired
    public MessageController(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/channel/send")
    public ResponseEntity<?> sendChannelMessage(@RequestBody MessageRequest request) {
        try {
            this.messageService.sendChannelMessage(request);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/send")
    public ResponseEntity<?> sendUserMessage(@RequestBody MessageRequest request) {
        try {
            this.messageService.sendUserMessage(request);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/channel/list")
    public ResponseEntity<?> getConversations() {
        try {
            return ResponseEntity.ok(this.messageService.findConversations());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/user/schedule")
    public ResponseEntity<?> scheduleUserMessage(@RequestBody MessageRequest request) {
        try {
            boolean response = messageService.scheduleUserMessage(request);
            if (response) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/channel/schedule")
    public ResponseEntity<?> scheduleChannelMessage(@RequestBody MessageRequest request) {
        try {
            boolean response = messageService.scheduleChannelMessage(request);
            if (response) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
