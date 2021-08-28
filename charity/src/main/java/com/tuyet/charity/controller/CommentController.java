package com.tuyet.charity.controller;

import com.tuyet.charity.pojo.Comment;
import com.tuyet.charity.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    public List<Comment> getAllCommentsPost(@RequestParam(value = "post-id") Integer postId){
        System.out.print(postId);
        return commentService.getAllCommentsPost(postId);
//        return null;
    }
}
