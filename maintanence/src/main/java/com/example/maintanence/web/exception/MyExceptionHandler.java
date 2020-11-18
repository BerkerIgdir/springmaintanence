package com.example.maintanence.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler{



    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<?> typeMismatchHandler(MethodArgumentTypeMismatchException ex){

       String BodyOfResponse = "The sent argument is out of format";
       return new ResponseEntity<>(BodyOfResponse + "\n" + ex.toString(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})

        protected ResponseEntity<?> IllegalArgumentHandler(IllegalArgumentException ex){

        String BodyOfResponse = "The sent argument is out of format";
        return new ResponseEntity<>(BodyOfResponse + "\n" + ex.toString(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<?> notFoundExceptionHandler(NotFoundException ex){

        String BodyOfResponse = "Could not be found";
        return new ResponseEntity<>(BodyOfResponse + "\n" + ex.toString(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<?> voidExceptionHandler(Exception ex){

        String BodyOfResponse = "An exception occured out of the reasons below";
        return new ResponseEntity<>(BodyOfResponse + "\n" + ex.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
