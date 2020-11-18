package com.example.maintanence.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "argument out of format")
public class NotFoundException extends RuntimeException{

}
