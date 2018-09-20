package fr.codechill.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)// 404
public class RessourceNotFoundException extends Exception {

	public RessourceNotFoundException (String message) {
		super(message);
	}
}