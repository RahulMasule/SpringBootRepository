package com.rahul.controllers;

import com.rahul.model.UserLoginRequest;
import com.rahul.service.MyUserDetailsService;
import com.rahul.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Hello User!";
    }

    @GetMapping("/protected")
    public String protectedEndpoint() {
        return "This Is protected Resource";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateAndGenerateToken(@RequestBody UserLoginRequest request) {
        Map<String, String> response = new HashMap<>();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtUtil.generateToken(userDetailsService.loadUserByUsername(request.getUsername()));
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            throw new UsernameNotFoundException("Invalid Credentials!");
        }
    }
}
