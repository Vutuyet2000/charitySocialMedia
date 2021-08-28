package com.tuyet.charity.service;

import com.tuyet.charity.pojo.PostAuction;

import java.util.List;

public interface PostAuctionService {
    List<PostAuction> getAllPostAuctions();
    void createPostAuction(PostAuction postAuction);

}
