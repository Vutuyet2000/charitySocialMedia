package com.tuyet.charity.repository.impl;

import com.tuyet.charity.pojo.Post;
import com.tuyet.charity.pojo.PostAuction;
import com.tuyet.charity.repository.PostCustomRepository;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PostRepositoryCustomImpl implements PostCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
//    @Transactional
    public void createPostWithPostAuction(PostAuction post) {
        entityManager.persist(post);
    }
}
