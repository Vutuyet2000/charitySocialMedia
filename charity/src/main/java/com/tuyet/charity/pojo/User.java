package com.tuyet.charity.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javafx.geometry.Pos;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(unique = true, nullable = false)
    private String username;

    @NotEmpty(message = "Password is required")
    @Column(nullable = false)
    private String password;

    @Pattern(regexp = "^[\\w+_-]+@((\\w+\\.\\w+)+)$",message = "Email is invalid")
    private String email;

    //=RoleEnum.ROLE_USER
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

//    @Column(name = "is_staff")
//    private boolean isStaff = false;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(nullable = true)
    private String avatar;

    //ManyToMany: user (Report)
    @OneToMany(mappedBy = "reporter")
    private List<Report> reporterReports;

    @OneToMany(mappedBy = "reportedUser")
    private List<Report> reportedReports;

    //ManyToOne:Post (owner)
    @OneToMany(mappedBy = "ownerPost")
    private List<Post> posts;

    //ManyToMany Post (comment,like,notification)
    //comment
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    //notification
    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    //like
    @OneToMany(mappedBy = "user")
    private List<Like> likes;

    public User(){}

    public User(String username, String password, String email, String avatar){
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public boolean isStaff() {
//        return isStaff;
//    }
//
//    public void setStaff(boolean staff) {
//        isStaff = staff;
//    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @JsonIgnore
    public List<Report> getReporterReports() {
        return reporterReports;
    }

    public void setReporterReports(List<Report> reporterReports) {
        this.reporterReports = reporterReports;
    }

    @JsonIgnore
    public List<Report> getReportedReports() {
        return reportedReports;
    }

    public void setReportedReports(List<Report> reportedReports) {
        this.reportedReports = reportedReports;
    }

    @JsonBackReference(value = "comment-user")
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

//    @JsonBackReference(value = "notification-user")
    @JsonIgnore
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

//    @JsonBackReference(value = "like-user")
    @JsonIgnore
    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

//    @JsonBackReference(value = "owner-post")
    @JsonIgnore
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
