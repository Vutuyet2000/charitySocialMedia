package com.tuyet.charity.repository;

import com.tuyet.charity.pojo.Post;
import com.tuyet.charity.pojo.PostAuction;

public interface PostCustomRepository {
    void createPostWithPostAuction(PostAuction post);
}
