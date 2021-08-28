package com.tuyet.charity.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "likes")
public class Like implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonBackReference(value = "like-post")
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

//    @JsonManagedReference(value = "like-user")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
