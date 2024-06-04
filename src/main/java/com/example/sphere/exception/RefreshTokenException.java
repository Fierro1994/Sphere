package com.example.sphere.exception;


public class RefreshTokenException extends RuntimeException {


    public RefreshTokenException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }

    public RefreshTokenException(String message) {
        super(message);
    }
}
