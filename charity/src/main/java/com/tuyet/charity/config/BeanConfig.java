package com.tuyet.charity.config;

import com.tuyet.charity.pojo.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConfig {
    @Bean
    public PostPagination createPostPagination(){
        PostPagination postPagination = new PostPagination();
        return postPagination;
    }

    @Bean
    @Scope("prototype")
    public PostAuction createNewPostAuction(){
        PostAuction postAuction = new PostAuction();
        return postAuction;
    }

    @Bean
    @Scope("prototype")
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
    @Scope("prototype")
    public Notification createNewNotification(){
        Notification notification = new Notification();
        return notification;
    }
}
