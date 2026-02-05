package com.example.stms_multi_user.advices;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.stms_multi_user.exceptions.ErrorResponse;
import com.example.stms_multi_user.exceptions.TaskNotFoundException;

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
}
