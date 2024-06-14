package com.example.sphere.exception;

import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

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

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Файл слишком большой! Максимальный размер файла 200MB.");
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

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseBody> handleUserNotFoundException(UsernameNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseBody> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((ErrorResponseBody.builder().message("Пользователя не существует, либо введены неверные данные!").build()));
    }

}
