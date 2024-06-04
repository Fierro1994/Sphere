package com.example.sphere.exception;

public class JwtAccessException extends RuntimeException{
    public JwtAccessException(String message) {
        super(message);
    }
}
