package com.zam.studypetclinic.services.impl;

import com.zam.studypetclinic.dto.UserRegisterRequest;
import com.zam.studypetclinic.entity.User;
import com.zam.studypetclinic.exceptions.ApiRequestException;
import com.zam.studypetclinic.repository.UserRepo;
import com.zam.studypetclinic.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) {
        return userRepo.findByUsername(username).map(user -> user)
                .orElseThrow(() -> new UsernameNotFoundException("user with username , " + username + " not found"));
    }

    @Override
    public User addUser(UserRegisterRequest userRequest) {
        Optional<User> userRepoByUsername = userRepo.findByUsername(userRequest.getUsername());
        if (userRepoByUsername.isPresent()) {
            throw new ApiRequestException("failed to register , username already been taken");
        } else {
            Optional<User> userRepoByEmail = userRepo.findByEmail(userRequest.getEmail());
            if (userRepoByEmail.isPresent()) {
                throw new ApiRequestException("failed to register, email already been taken");
            }
        }
        var user = User.builder()
                .address(userRequest.getAddress())
                .username(userRequest.getUsername())
                .name(userRequest.getName())
                .password(passwordEncoder.encode(userRequest.getPassword())).
                build();
        userRepo.save(user);
        return user;
    }
}
