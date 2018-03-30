package fr.codechill.spring.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR,reason="Our server met a problem")// 500
public class ServerErrorException extends Exception{
	private String message;
    
    public ServerErrorException (String message){
		super(message);
		this.message = message;
	}
	
	public String getMessage(){
		return this.message;
    }
}
