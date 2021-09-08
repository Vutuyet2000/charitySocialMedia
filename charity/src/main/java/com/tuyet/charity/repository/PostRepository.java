package com.tuyet.charity.repository;

import com.tuyet.charity.pojo.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@Repository
@EnableTransactionManagement
public interface PostRepository extends JpaRepository<Post,Integer> {
    Page<Post> findAllByOrderByCreatedDateDesc(Pageable pageable);

    Post findFirstByOrderByCreatedDateDesc();
}
