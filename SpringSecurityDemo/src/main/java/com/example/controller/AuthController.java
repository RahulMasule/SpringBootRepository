package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/login")
    public  String login(){
        return  "login";
    }

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "logout";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }

    @GetMapping("/404")
    public String notFound() {
        return "404";
    }
}
