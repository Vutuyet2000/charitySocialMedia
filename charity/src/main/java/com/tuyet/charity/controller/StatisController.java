package com.tuyet.charity.controller;

import com.tuyet.charity.service.CommentService;
import com.tuyet.charity.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class StatisController {
    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    //chi cho phep admin
    @GetMapping("/statistics")
    public ResponseEntity<Object> getStatistics(@RequestParam("year") int year){
        Map<String, Integer> statistics = new HashMap<>();
        //lay so post trong nam
        int countPosts = postService.countYearPost(year);
        statistics.put("posts",countPosts);
        //lay so comment trong nam
        int countComments = commentService.countCommentInYear(year);
        statistics.put("comments",countComments);

        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }
}
