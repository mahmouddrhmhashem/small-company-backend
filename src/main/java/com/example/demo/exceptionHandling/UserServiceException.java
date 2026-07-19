package com.example.demo.exceptionHandling;


public class UserServiceException extends RuntimeException{
    public UserServiceException(String message)
    {
        super(message);
    }
}