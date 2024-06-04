package com.example.Sphere.exception;

public class JwtAccessException extends RuntimeException{
    public JwtAccessException(String message) {
        super(message);
    }
}
