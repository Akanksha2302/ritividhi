package com.dev.ritividhi.controllers;

import com.dev.ritividhi.models.Notification;
import com.dev.ritividhi.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ritividhi/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Create a new notification
    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
        return notificationService.createNotification(notification);
    }

    // Get a notification by ID
    @GetMapping("/{id}")
    public Notification getNotificationById(@PathVariable String id) {
        return notificationService.getNotificationById(id);
    }

    // Get all notifications
    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    // Get notifications by userId
    @GetMapping("/user/{userId}")
    public List<Notification> getNotificationsByUserId(@PathVariable String userId) {
        return notificationService.getNotificationsByUserId(userId);
    }

    // Get notifications by eventName
    @GetMapping("/event/{eventName}")
    public List<Notification> getNotificationsByEventName(@PathVariable String eventName) {
        return notificationService.getNotificationsByEventName(eventName);
    }

    // Update a notification
    @PutMapping("/{id}")
    public Notification updateNotification(@PathVariable String id, @RequestBody Notification notification) {
        return notificationService.updateNotification(id, notification);
    }

    // Delete a notification
    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable String id) {
        notificationService.deleteNotification(id);
    }
}
