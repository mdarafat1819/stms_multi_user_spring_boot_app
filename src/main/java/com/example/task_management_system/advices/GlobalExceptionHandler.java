package com.example.task_management_system.advices;

import java.time.LocalDateTime;

import javax.security.sasl.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.task_management_system.exceptions.ErrorResponse;
import com.example.task_management_system.exceptions.TaskNotFoundException;
import com.example.task_management_system.exceptions.UserAlreadyExistsException;
import com.example.task_management_system.exceptions.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFound(
        HttpServletRequest request,
        TaskNotFoundException ex
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

     @ExceptionHandler(HttpMessageNotReadableException.class)
   public ResponseEntity<ErrorResponse> handleNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
     ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(AuthorizationDeniedException.class) 
   public ResponseEntity<ErrorResponse>handleAuthorizationDenied(
    HttpServletRequest request,
    AuthorizationDeniedException ex
   ) {
      ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.FORBIDDEN.value(),
            HttpStatus.FORBIDDEN.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);

   }

   @ExceptionHandler(UserAlreadyExistsException.class)
   public ResponseEntity<ErrorResponse>handleUserAlreadyExists(
    HttpServletRequest request,
    UserAlreadyExistsException ex
   ) {
     ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(UserNotFoundException.class)
   public ResponseEntity<ErrorResponse>handleUserNotFound(
    HttpServletRequest request,
    UserNotFoundException ex
   ) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
        HttpStatus.NOT_FOUND.getReasonPhrase(),
        ex.getMessage(),
        request.getRequestURI()
    );

    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler(UsernameNotFoundException.class)
   public ResponseEntity<ErrorResponse>handleUsernameNotFound(
    HttpServletRequest request,
    UsernameNotFoundException ex
   ) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
        HttpStatus.NOT_FOUND.getReasonPhrase(),
        ex.getMessage(),
        request.getRequestURI()
    );

    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler(BadCredentialsException.class)
   public ResponseEntity<ErrorResponse>handleAuthenticationException(
    HttpServletRequest request,
    BadCredentialsException ex
   ) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value()
        , HttpStatus.UNAUTHORIZED.getReasonPhrase()
        ,ex.getMessage(), request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
   }

   @ExceptionHandler(RuntimeException.class)
   public ResponseEntity<ErrorResponse>handleOtherExceptions(
    HttpServletRequest request,
    RuntimeException ex
   ) {
     ErrorResponse errorResponse = new ErrorResponse(
            9999,
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            "An unexpected error occurred: " + ex.getMessage(),
            request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
   }
}
