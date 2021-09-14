package com.tuyet.charity.repository;

import com.tuyet.charity.pojo.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like,Integer> {
    List<Like> findByPostPostId(Integer postId);
}
