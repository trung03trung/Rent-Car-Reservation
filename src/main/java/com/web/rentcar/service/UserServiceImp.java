package com.web.rentcar.service;
import com.web.rentcar.CustomUserDetails.CustomUserDetails;
import com.web.rentcar.entity.Users;
import com.web.rentcar.repository.usersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImp implements UserDetailsService {
    @Autowired
    private usersRepository userRepo;



    public boolean usersExists(String email){
            if(userRepo.findByEmail(email)!=null){
                return true;
            }
            return false;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user=userRepo.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new customUserDetails(user);

    }
    public Users getCurrentlyLoggedInUser(Authentication authentication){
        if (authentication==null)
            return null;
        Users user=null;
        Object principal=authentication.getPrincipal();
        if(principal instanceof customUserDetails)
            user=((customUserDetails) principal).getUser();
        return user;
    }


}
