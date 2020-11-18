package com.maintanence.malfunctiongateway.web.exception;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "There is no employee available")
@Data
public class NoAvailableEmployeeException extends RuntimeException{

    private final String errorMessage;

   public NoAvailableEmployeeException(String message){
        super(message);
        this.errorMessage = message;
    }

}
