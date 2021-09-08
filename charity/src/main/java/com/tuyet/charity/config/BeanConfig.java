package com.tuyet.charity.config;

import com.tuyet.charity.pojo.Post;
import com.tuyet.charity.pojo.PostAuction;
import com.tuyet.charity.pojo.PostPagination;
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
}
