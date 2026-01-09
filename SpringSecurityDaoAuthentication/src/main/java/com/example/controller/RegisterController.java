package com.example.controller;

import com.example.entities.MyUser;
import com.example.model.UserLoginRequest;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register-user")
    public MyUser registerNewUser(@RequestBody UserLoginRequest loginRequest) {

        if (userRepository.existsByUserName(loginRequest.getUsername())) {
            throw new RuntimeException("User already exist");
        }
        MyUser myUser = new MyUser();
        myUser.setUserName(loginRequest.getUsername());
        myUser.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
        myUser.setRoles(loginRequest.getRoles());
//        myUser.setRoles("ROLE_" + loginRequest.getRoles().toUpperCase());
        return userRepository.save(myUser);
    }

}
