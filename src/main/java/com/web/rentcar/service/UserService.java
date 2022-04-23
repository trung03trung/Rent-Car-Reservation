package com.web.rentcar.service;


import com.web.rentcar.entity.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    boolean usersExists(String email);
}
