package com.manollo.myapp.security.service;

/**
 * SecurityService
 */
public interface SecurityService {
    String findLoogedInUsername();
    void autoLogin(String username, String password);    
}