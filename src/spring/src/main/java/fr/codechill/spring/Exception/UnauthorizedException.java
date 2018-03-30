package fr.codechill.spring.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED,reason="Unauthorized")//401
public class UnauthorizedException extends Exception{
	private String message;
    
    public UnauthorizedException(String message){
		super(message);
		this.message = message;
	}
	
	public String getMessage(){
		return this.message;
	}

}
