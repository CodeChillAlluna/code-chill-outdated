package fr.codechill.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN) // 403
public class AccessDeniedException extends Exception {

    public AccessDeniedException (String message) {
		super(message);
	}
}