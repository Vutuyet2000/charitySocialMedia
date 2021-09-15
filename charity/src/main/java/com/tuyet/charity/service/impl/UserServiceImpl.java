package com.tuyet.charity.service.impl;

import com.tuyet.charity.pojo.RoleEnum;
import com.tuyet.charity.pojo.User;
import com.tuyet.charity.repository.UserRepository;
import com.tuyet.charity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = this.getUsers(username);
        if(users.isEmpty())
            throw new UsernameNotFoundException("User does not exist");

        User user = users.get(0);
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User addUser(User user) {
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        if (user.getRole()==null) {
            user.setRole(RoleEnum.ROLE_USER);
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers(String username) {
        return userRepository.findAllByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getCurrentUser(String username) {
        return userRepository.findOneByUsername(username);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findOneByUserId(id);
    }
}
