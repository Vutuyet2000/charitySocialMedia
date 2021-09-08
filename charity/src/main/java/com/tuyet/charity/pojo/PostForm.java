package com.tuyet.charity.pojo;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class PostForm {
    private Integer postId;

    @NotEmpty(message = "content is required")
    private String content;

    private MultipartFile image;

    private Date createdDate = new Date(System.currentTimeMillis());

    private Set<HashTagEnum> hashTag;

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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Set<HashTagEnum> getHashTag() {
        return hashTag;
    }

    public void setHashTag(Set<HashTagEnum> hashTag) {
        this.hashTag = hashTag;
    }
}
