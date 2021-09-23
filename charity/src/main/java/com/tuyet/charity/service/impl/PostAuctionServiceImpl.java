package com.tuyet.charity.service.impl;

import com.tuyet.charity.pojo.PostAuction;
import com.tuyet.charity.repository.PostAuctionCustomRespository;
import com.tuyet.charity.repository.PostAuctionRepository;
import com.tuyet.charity.service.PostAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostAuctionServiceImpl implements PostAuctionService {
    @Autowired
    private PostAuctionRepository postAuctionRepository;

    @Autowired
    private PostAuctionCustomRespository postAuctionCustomRespository;

    @Override
    public List<PostAuction> getAllPostAuctions() {
        return postAuctionRepository.findAll();
    }

    @Override
    public void createPostAuction(PostAuction postAuction) {
        postAuctionRepository.save(postAuction);
    }

    @Override
    @Transactional
    public void createNewPostAuction(PostAuction postAuction) {
        postAuctionCustomRespository.createNewPostAuction(postAuction);
    }
}
