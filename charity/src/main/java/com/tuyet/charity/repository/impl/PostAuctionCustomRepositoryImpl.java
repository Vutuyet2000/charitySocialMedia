package com.tuyet.charity.repository.impl;

import com.tuyet.charity.pojo.PostAuction;
import com.tuyet.charity.repository.PostAuctionCustomRespository;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PostAuctionCustomRepositoryImpl implements PostAuctionCustomRespository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void createNewPostAuction(PostAuction postAuction) {
//        org.hibernate.Session session = (Session) entityManager.getDelegate();
//        session.evict(postAuction);
        entityManager.merge(postAuction);
//        entityManager.persist(postAuction);
    }
}
