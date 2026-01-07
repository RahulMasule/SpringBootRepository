package com.example.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalErrorController {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle404Page(){
        return "404";
    }
}
