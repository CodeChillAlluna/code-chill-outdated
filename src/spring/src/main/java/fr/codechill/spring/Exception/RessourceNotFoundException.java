package fr.codechill.spring.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST,reason="Parameters error")// 404
public class RessourceNotFoundException extends Exception{
	private String message;
    
    public RessourceNotFoundException (String message){
		super(message);
		this.message = message;
	}
	
	public String getMessage(){
		return this.message;
    }
}