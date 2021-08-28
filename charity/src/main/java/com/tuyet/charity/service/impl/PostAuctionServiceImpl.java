package com.tuyet.charity.service.impl;

import com.tuyet.charity.pojo.PostAuction;
import com.tuyet.charity.repository.PostAuctionRepository;
import com.tuyet.charity.service.PostAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostAuctionServiceImpl implements PostAuctionService {
    @Autowired
    private PostAuctionRepository postAuctionRepository;

    @Override
    public List<PostAuction> getAllPostAuctions() {
        return postAuctionRepository.findAll();
    }

    @Override
    public void createPostAuction(PostAuction postAuction) {
        postAuctionRepository.save(postAuction);
    }
}
