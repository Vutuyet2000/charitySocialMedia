package com.tuyet.charity.service;

import com.tuyet.charity.pojo.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getAllNotificationsPost(Integer userId);

    Notification getNotificationById(Integer notificationId);

    void createNotification(Notification notification);
}
