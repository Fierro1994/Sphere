package com.example.Sphere.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class WebAppExceptionHandling {

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<ErrorResponseBody> refreshTokenExceptionHandler (TokenRefreshException ex, WebRequest webRequest){
        return buildResponse(HttpStatus.FORBIDDEN, ex, "Неактивный refresh токен");
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorResponseBody> allreadyExistException (AlreadyExistException ex, WebRequest webRequest){
        return buildResponse(HttpStatus.BAD_REQUEST, ex, "Ошибка");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseBody> entityNotFoundException (EntityNotFoundException ex, WebRequest webRequest){
        return buildResponse(HttpStatus.NOT_FOUND, ex, "not found");
    }

    private ResponseEntity<ErrorResponseBody> buildResponse(HttpStatus httpStatus, Exception ex, String string) {
   return ResponseEntity.status(httpStatus)
           .body(ErrorResponseBody.builder().message(string).description(ex.getMessage()).build());
    }
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<ErrorResponseBody>  internalAuthenticationServiceExceptionResponseEntity (InternalAuthenticationServiceException ex, WebRequest request){
        return buildResponse(HttpStatus.NOT_FOUND, ex, "Пользователь не найден");
    }


}
