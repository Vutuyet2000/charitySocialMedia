package com.tuyet.charity.service;

import com.tuyet.charity.pojo.Like;

import java.util.List;

public interface LikeService {
    List<Like> getAllLikesPost(Integer postId);
}
