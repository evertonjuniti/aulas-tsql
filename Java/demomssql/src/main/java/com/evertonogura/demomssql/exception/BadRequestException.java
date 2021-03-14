package com.evertonogura.demomssql.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {

	private static final long serialVersionUID = 6374198702892912283L;
	
	public BadRequestException(String message){
        super(message);
    }
	
}
