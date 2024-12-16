package com.harsha.scv_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class StatusNotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public StatusNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyStatus(String status) {
        System.out.println("Broadcasting status: " + status);
        messagingTemplate.convertAndSend("/topic/status", status);
    }

}
