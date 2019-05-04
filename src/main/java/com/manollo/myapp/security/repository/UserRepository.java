package com.manollo.myapp.security.repository;

import com.manollo.myapp.security.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}