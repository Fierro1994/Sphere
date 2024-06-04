package com.example.sphere.exception;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException (String message){
    super(message);
    }
}
