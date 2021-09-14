package com.tuyet.charity.service;

import com.tuyet.charity.pojo.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAllCommentsPost(Integer postId);

    void deleteComment(Comment comment);

    Comment getCommentById(Integer commentId);

    Comment createComment(Comment comment);
}
