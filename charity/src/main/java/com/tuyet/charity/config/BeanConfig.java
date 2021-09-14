package com.tuyet.charity.config;

import com.tuyet.charity.pojo.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public PostPagination createPostPagination(){
        PostPagination postPagination = new PostPagination();
        return postPagination;
    }

    @Bean
    public PostAuction createNewPostAuction(){
        PostAuction postAuction = new PostAuction();
        return postAuction;
    }

    @Bean
    public Post createNewPost(){
        Post post = new Post();
        return post;
    }

    @Bean
    public Like createNewLike(){
        Like like = new Like();
        return like;
    }

    @Bean
    public Notification createNewNotification(){
        Notification notification = new Notification();
        return notification;
    }
}
