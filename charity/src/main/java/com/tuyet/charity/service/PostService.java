package com.tuyet.charity.service;

import com.tuyet.charity.pojo.Post;
import com.tuyet.charity.pojo.PostAuction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    int getAmountAllPosts();
    Page<Post> getAllPosts(int currentPage);
    Post createPost(Post post);
//    void createPostWithPostAuction(PostAuction post);
    Post getCreatedPos();
    Post getPost(Integer id);
    void deletePost(Integer id);
}
