package com.manollo.myapp.security.repository;

import com.manollo.myapp.security.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RoleRepository
 */
public interface RoleRepository extends JpaRepository<Role, Long>{
}