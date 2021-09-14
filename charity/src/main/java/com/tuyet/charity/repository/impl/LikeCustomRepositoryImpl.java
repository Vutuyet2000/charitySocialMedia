package com.tuyet.charity.repository.impl;

import com.tuyet.charity.pojo.Like;
import com.tuyet.charity.pojo.Post;
import com.tuyet.charity.pojo.PostAuction;
import com.tuyet.charity.repository.LikeCustomRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class LikeCustomRepositoryImpl implements LikeCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void createNewLike(Like like) {
        entityManager.merge(like);
    }

    @Override
    @Transactional
    public void deleteLike(Post post, Integer likeId) {
        post.getLikes().removeIf(like -> like.getId() == likeId);
        entityManager.persist(post);
    }
}
