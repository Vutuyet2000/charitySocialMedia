package com.tuyet.charity.pojo;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer postId;

    @NotEmpty(message = "content is required")
    @Column(nullable = true)
    private String content;

    @Column(nullable = true)
    private String image;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = true)
    private Date createdDate = new Date(System.currentTimeMillis());

    @Enumerated(EnumType.STRING)
    @Column(name="hash_tag")
    private HashTagEnum hashTag;

    //ManyToOne: user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User ownerPost;

    //Many to many: user (like, comment, notification);
    //comment
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    //notification
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    //like
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Like> likes;

    //OneToOne: postAuction
    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn //The PrimaryKeyJoinColumn annotation specifies a primary key column that is used as a foreign key to join to another table.
    private PostAuction postAuction;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public HashTagEnum getHashTag() {
        return hashTag;
    }

    public void setHashTag(HashTagEnum hashTag) {
        this.hashTag = hashTag;
    }

    @JsonManagedReference(value = "post-postauction")
    public PostAuction getPostAuction() {
        return postAuction;
    }

    public void setPostAuction(PostAuction postAuction) {
        this.postAuction = postAuction;
    }

    @JsonManagedReference(value = "comment-post")
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @JsonManagedReference(value = "notification-post")
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @JsonManagedReference(value = "like-post")
    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

//    @JsonManagedReference(value = "owner-post")
    public User getOwnerPost() {
        return ownerPost;
    }

    public void setOwnerPost(User ownerPost) {
        this.ownerPost = ownerPost;
    }
}
