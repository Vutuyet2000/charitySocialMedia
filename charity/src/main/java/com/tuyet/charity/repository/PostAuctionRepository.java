package com.tuyet.charity.repository;

import com.tuyet.charity.pojo.PostAuction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostAuctionRepository extends JpaRepository<PostAuction,Integer> {
}
