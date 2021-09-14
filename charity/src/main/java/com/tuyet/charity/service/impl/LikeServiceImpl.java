package com.tuyet.charity.service.impl;

import com.tuyet.charity.pojo.Like;
import com.tuyet.charity.pojo.Post;
import com.tuyet.charity.repository.LikeCustomRepository;
import com.tuyet.charity.repository.LikeRepository;
import com.tuyet.charity.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private LikeCustomRepository likeCustomRepository;

    @Override
    public List<Like> getAllLikesPost(Integer postId) {
        return likeRepository.findByPostPostId(postId);
    }

    @Override
    public Like getLike(Integer likeId) {
        return likeRepository.findById(likeId).get();
    }

    @Override
    public void createLike(Like like) {
        likeCustomRepository.createNewLike(like);
    }

    @Override
    @Transactional
    public void deleteLike(Post post, Integer likeId) {
        likeCustomRepository.deleteLike(post,likeId);
    }
}
