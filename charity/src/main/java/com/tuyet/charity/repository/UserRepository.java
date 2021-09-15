package com.tuyet.charity.repository;

import com.tuyet.charity.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByUsername(String username);

    User findOneByUsername(String username);

    User findOneByUserId(Integer id);
}
