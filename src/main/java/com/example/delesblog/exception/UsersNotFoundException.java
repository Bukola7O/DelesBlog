package com.example.delesblog.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;


public class UsersNotFoundException extends Exception{
    private String message;
    public UsersNotFoundException(String message) {
        this.message = message;
    }
}
