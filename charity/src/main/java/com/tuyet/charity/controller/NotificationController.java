package com.tuyet.charity.controller;

import com.tuyet.charity.pojo.Notification;
import com.tuyet.charity.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notifications")
    public List<Notification> getAllNotificationsPost(@RequestParam("post-id") Integer postId){
        return notificationService.getAllNotificationsPost(postId);
    }
}
