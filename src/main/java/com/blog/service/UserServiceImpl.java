package com.blog.service;

import com.blog.dao.UserRepository;
import com.blog.po.User;
import com.blog.util.MD5util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5util.code(password+username+username));
        return user;
    }
}

