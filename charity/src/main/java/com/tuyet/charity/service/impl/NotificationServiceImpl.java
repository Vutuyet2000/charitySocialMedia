package com.tuyet.charity.service.impl;

import com.tuyet.charity.pojo.Notification;
import com.tuyet.charity.repository.NotificationRepository;
import com.tuyet.charity.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<Notification> getAllNotificationsPost(Integer userId) {
        return notificationRepository.findByActiveTrueAndUserUserId(userId);
    }

    @Override
    public Notification getNotificationById(Integer notificationId) {
        return notificationRepository.findByNotificationId(notificationId);
    }

    @Override
    public void createNotification(Notification notification) {
        notificationRepository.save(notification);
    }
}
