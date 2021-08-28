package com.tuyet.charity.service;

import com.tuyet.charity.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void addUser(User user);
    List<User> getUsers(String username);
}
