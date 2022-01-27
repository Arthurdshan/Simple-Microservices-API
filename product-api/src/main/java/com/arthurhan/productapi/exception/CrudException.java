package com.arthurhan.productapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CrudException extends RuntimeException
{
    public CrudException(String message)
    {
        super(message);
    }
}
