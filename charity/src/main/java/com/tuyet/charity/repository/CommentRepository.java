package com.tuyet.charity.repository;

import com.tuyet.charity.pojo.Comment;
import com.tuyet.charity.pojo.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findByPostPostId(Integer postId);
}
