package fr.codechill.spring.exception;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler(
        {
            AccessDeniedException.class,
            BadRequestException.class,
            RessourceNotFoundException.class,
            ServerErrorException.class,
            UnauthorizedException.class
        })
    @ResponseBody
	ResponseEntity <ExceptionRepresentation> handle(Exception exception) {
        ExceptionRepresentation body = new ExceptionRepresentation(exception.getLocalizedMessage());
        HttpStatus responseStatus = resolveAnnotatedResponseStatus(exception);
        return new ResponseEntity <ExceptionRepresentation> (body, responseStatus);
    }

    HttpStatus resolveAnnotatedResponseStatus(Exception exception) {
      ResponseStatus annotation = findMergedAnnotation(exception.getClass(), ResponseStatus.class);
        if (annotation != null) {
            return annotation.value();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}