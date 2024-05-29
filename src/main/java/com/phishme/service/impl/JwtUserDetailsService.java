package com.phishme.service.impl;

import com.phishme.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(userInfoRepository.existsByEmail(username)) {
            System.err.println("in JwtUserDetailsService, loaded userName : "+username);
            return new User(username, userInfoRepository.findByEmail(username).getPassword(), new ArrayList<>());
        }
        else throw new UsernameNotFoundException("User not found with userName: " + username);
    }
}
