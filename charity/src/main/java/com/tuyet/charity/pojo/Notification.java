package com.tuyet.charity.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "notification")
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Integer notificationId;

    @Column(nullable = false)
    private Integer type;

    private boolean active=true;

    //Many to many Post, User
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @JsonBackReference(value = "notification-post")
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

//    @JsonManagedReference(value = "notification-user")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
