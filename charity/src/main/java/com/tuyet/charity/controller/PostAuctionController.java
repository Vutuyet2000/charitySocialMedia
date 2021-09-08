package com.tuyet.charity.controller;

import com.tuyet.charity.pojo.Post;
import com.tuyet.charity.pojo.PostAuction;
import com.tuyet.charity.service.PostAuctionService;
import com.tuyet.charity.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class PostAuctionController {
    @Autowired
    private PostAuctionService postAuctionService;

    @GetMapping("/post-auction")
    public List<PostAuction> getAllPostAuctions(){
        return postAuctionService.getAllPostAuctions();
    }

    @PostMapping("/post-auction")
    public void createPostAuctions(@RequestBody PostAuction postAuction, @PathVariable Integer post_id){
        postAuctionService.createPostAuction(postAuction);
    }
}
