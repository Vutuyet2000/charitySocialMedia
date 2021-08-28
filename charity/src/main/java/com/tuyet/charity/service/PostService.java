package com.tuyet.charity.service;

import com.tuyet.charity.pojo.Post;
import com.tuyet.charity.pojo.PostAuction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    Page<Post> getAllPosts();
    void createPost(Post post);
    void createPostWithPostAuction(PostAuction post);
    Post getCreatedPos();
    Post getPost(Integer id);
    void deletePost(Integer id);
}
