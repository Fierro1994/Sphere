package com.example.Sphere.exception;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class WebAppExceptionHandling {

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<ErrorResponseBody> refreshTokenExceptionHandler (RefreshTokenException ex, WebRequest webRequest){
        return buildResponse(HttpStatus.FORBIDDEN, ex);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorResponseBody> allreadyExistException (AlreadyExistException ex, WebRequest webRequest){
        return buildResponse(HttpStatus.BAD_REQUEST, ex);
    }



    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseBody> entityNotFoundException (EntityNotFoundException ex, WebRequest webRequest){
        return buildResponse(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<ErrorResponseBody> internalAuthenticationServiceExceptionResponseEntity(InternalAuthenticationServiceException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex);
    }
    @ExceptionHandler(JwtAccessException.class)
    public ResponseEntity<ErrorResponseBody> jwtAccessException(JwtAccessException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(SignatureException.class)
    protected ResponseEntity<ErrorResponseBody> handleMethodArgumentNotValid(
            SignatureException ex, WebRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((ErrorResponseBody.builder().message("Acccess denied").build()));
    }
    @ExceptionHandler(MissingRequestCookieException.class)
    protected ResponseEntity<ErrorResponseBody> MissingRequestCookieException(
            MissingRequestCookieException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((ErrorResponseBody.builder().message("Refresh token not present").build()));
    }

    private ResponseEntity<ErrorResponseBody> buildResponse(HttpStatus httpStatus, Exception ex) {
        return ResponseEntity.status(httpStatus)
                .body(ErrorResponseBody.builder().message(ex.getMessage()).description("Error").build());
    }


}
