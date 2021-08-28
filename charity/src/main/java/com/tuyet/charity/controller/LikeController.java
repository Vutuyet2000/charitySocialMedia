package com.tuyet.charity.controller;

import com.tuyet.charity.pojo.Like;
import com.tuyet.charity.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LikeController {
    @Autowired
    private LikeRepository likeRepository;

    @GetMapping("/likes")
    public List<Like> getAllLikesPost(@RequestParam("post-id") Integer postId){
        return likeRepository.findByPostPostId(postId);
    }


}
