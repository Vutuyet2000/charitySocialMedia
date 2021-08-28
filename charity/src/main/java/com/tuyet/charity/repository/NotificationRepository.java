package com.tuyet.charity.repository;

import com.tuyet.charity.pojo.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    List<Notification> findByPostPostId(Integer postId);
}
