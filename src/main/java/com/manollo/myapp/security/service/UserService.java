package com.manollo.myapp.security.service;

import com.manollo.myapp.security.entity.User;

/**
 * UserService
 */
public interface UserService {
    void save(User user);
    User findByUsername(String username);
}