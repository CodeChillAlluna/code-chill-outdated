package fr.codechill.spring.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST,reason="Parameters error")// 400
public class BadRequestException extends Exception{
	private String message;
    
    public BadRequestException(String message){
		super(message);
		this.message = message;
	}
	
	public String getMessage(){
		return this.message;
	}

}
