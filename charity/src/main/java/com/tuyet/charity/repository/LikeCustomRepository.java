package com.tuyet.charity.repository;

import com.tuyet.charity.pojo.Like;
import com.tuyet.charity.pojo.Post;
import com.tuyet.charity.pojo.PostAuction;

public interface LikeCustomRepository {
    void createNewLike(Like like);
    void deleteLike(Post post, Integer likeId);
}
