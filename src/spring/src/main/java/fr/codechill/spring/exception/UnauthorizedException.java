package fr.codechill.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED) //401
public class UnauthorizedException extends Exception {

    public UnauthorizedException(String message) {
		super(message);
	}

}
