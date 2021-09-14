package com.tuyet.charity.service.impl;

import com.tuyet.charity.pojo.Comment;
import com.tuyet.charity.repository.CommentRepository;
import com.tuyet.charity.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getAllCommentsPost(Integer postId) {
        return commentRepository.findByPostPostId(postId);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public Comment getCommentById(Integer commentId) {
        return commentRepository.findById(commentId).get();
    }

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
