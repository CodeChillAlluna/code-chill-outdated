package fr.codechill.spring.exception;

import java.io.Serializable;

public class ExceptionRepresentation implements Serializable {
    String message;

    public ExceptionRepresentation (String m){
        this.message = m;
    }

    public String getMessage() {
        return this.message;
    }

}