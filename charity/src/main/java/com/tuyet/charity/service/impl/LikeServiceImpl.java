package com.tuyet.charity.service.impl;

import com.tuyet.charity.pojo.Like;
import com.tuyet.charity.repository.LikeRepository;
import com.tuyet.charity.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeRepository likeRepository;

    @Override
    public List<Like> getAllLikesPost(Integer postId) {
        return likeRepository.findByPostPostId(postId);
    }
}
