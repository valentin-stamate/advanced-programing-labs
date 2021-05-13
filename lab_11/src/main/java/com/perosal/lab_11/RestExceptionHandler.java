package com.perosal.lab_11;

import com.perosal.lab_11.request.ResponseSuccess;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.io.IOException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = { IOException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseSuccess badRequest(Exception e) {
        e.printStackTrace();
        return new ResponseSuccess("Sad request");
    }

    @ExceptionHandler(value = { NullPointerException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseSuccess nullPointer(NullPointerException e) {
        e.printStackTrace();
        return new ResponseSuccess("Life cannot be null");
    }
}
