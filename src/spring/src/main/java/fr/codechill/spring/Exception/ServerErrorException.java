package fr.codechill.spring.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR) // 500
public class ServerErrorException extends Exception {

    public ServerErrorException (String message) {
		super(message);
	}

}
