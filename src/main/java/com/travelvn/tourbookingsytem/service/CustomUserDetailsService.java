package com.travelvn.tourbookingsytem.service;


import com.travelvn.tourbookingsytem.model.UserAccount;
import com.travelvn.tourbookingsytem.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        if (userAccount == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(userAccount.getUsername(), userAccount.getPassword(),
                Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority(userAccount.getRole().toString())));
    }
}