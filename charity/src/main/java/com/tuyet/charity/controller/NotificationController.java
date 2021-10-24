package com.tuyet.charity.controller;

import com.tuyet.charity.pojo.Notification;
import com.tuyet.charity.pojo.User;
import com.tuyet.charity.service.NotificationService;
import com.tuyet.charity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userDetailsService;

//    @GetMapping("/notifications")
//    public List<Notification> getAllNotificationsPost(@RequestParam("post-id") Integer postId){
//        return notificationService.getAllNotificationsPost(postId);
//    }
    @GetMapping("/notifications")
    public List<Notification> getAllNotificationsPost(OAuth2Authentication auth){
        User ownerPost = userDetailsService.getCurrentUser(auth.getName());
        
        return notificationService.getAllNotificationsPost(ownerPost.getUserId());
    }

    //inactive notification (khi user đã seen notification)
    @PostMapping("/notifications/{notificationId}")
    public ResponseEntity<Object> inactiveNotification(@PathVariable int notificationId, OAuth2Authentication auth){
        Notification notification = notificationService.getNotificationById(notificationId);
        //kt notifiaction co ton tai
        if (notification == null) {
            Map<String, String> msg = new HashMap<>();
            msg.put("error", "this notification does not exist");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        //kt current user co phai la user nhan notification
        if(!notification.getUser().getUsername().equals(auth.getName())){
            Map<String, String> msg = new HashMap<>();
            msg.put("error","this user does not have permission");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        notification.setActive(false);

        //luu notification cap nhat vao db
        notificationService.createNotification(notification);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
