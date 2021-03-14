package com.alkemy.labs.services;

import com.alkemy.labs.models.User;
import com.alkemy.labs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                isActive(user.getActivo()),
                isActive(user.getActivo()),
                isActive(user.getActivo()),
                isActive(user.getActivo()),
                buildGrante(user.getRol()));
    }

    public List<GrantedAuthority> buildGrante(int authority) {
        String[] roles = {"ADMIN", "STUDENT"};
        List<GrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority(roles[authority]));
        return auths;
    }

    public boolean isActive(int active) {
        if (active == 1) {
            return true;
        } else {
            return false;
        }
    }
}
