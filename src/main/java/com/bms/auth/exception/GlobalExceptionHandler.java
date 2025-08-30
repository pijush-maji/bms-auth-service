package com.bms.auth.exception;

import com.bms.auth.dto.CustomExceptionResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception ex){
        ProblemDetail problemDetail=null;
        ex.printStackTrace();
        if(ex instanceof ExpiredJwtException){
            problemDetail=ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED,ex.getMessage());
            problemDetail.setProperty("message","Token has expired");
        }
        if(ex instanceof SignatureException){
            problemDetail=ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED,ex.getMessage());
            problemDetail.setProperty("message","Token is not valid");
        }
        return problemDetail;
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<CustomExceptionResponse> handleInvalidTokenException(InvalidTokenException ex){
        CustomExceptionResponse exceptionResponse = new CustomExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BmsAuthServiceException.class)
    public ResponseEntity<CustomExceptionResponse> handleBmsAuthServiceException(BmsAuthServiceException ex){
        CustomExceptionResponse res = new CustomExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BmsUserServiceException.class)
    public ResponseEntity<CustomExceptionResponse> handleBmsUserServiceException(BmsUserServiceException ex){
        CustomExceptionResponse res = new CustomExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
    }
}
