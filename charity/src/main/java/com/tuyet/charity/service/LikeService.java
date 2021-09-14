package com.tuyet.charity.service;

import com.tuyet.charity.pojo.Like;
import com.tuyet.charity.pojo.Post;

import java.util.List;

public interface LikeService {
    List<Like> getAllLikesPost(Integer postId);

    Like getLike(Integer likeId);

    void createLike(Like like);

    void deleteLike(Post post, Integer likeId);
}
