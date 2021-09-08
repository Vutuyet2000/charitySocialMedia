package com.tuyet.charity.service;

import com.tuyet.charity.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User addUser(User user);
    List<User> getUsers(String username);
    List<User> getAllUsers();
    User getCurrentUser(String username);
    User getUserByUsername(String username);
    User getUserById(Integer id);
}
