package ru.sanctio.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchDataException.class)
    public ResponseEntity<IncorrectData> handleNoSuchDataException(NoSuchDataException e) {
        IncorrectData incorrectData = new IncorrectData(e.getMessage());
        return new ResponseEntity<>(incorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RepeatingAddressException.class)
    public ResponseEntity<IncorrectData> handleRepeatingAddressException(RepeatingAddressException e) {
        IncorrectData incorrectData = new IncorrectData(e.getMessage());
        return new ResponseEntity<>(incorrectData, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<IncorrectData> handleIllegalArgumentException(IllegalArgumentException e) {
        IncorrectData incorrectData = new IncorrectData(e.getMessage());
        return new ResponseEntity<>(incorrectData, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<IncorrectData> handleIllegalStateException(IllegalStateException e) {
        IncorrectData incorrectData = new IncorrectData(e.getMessage());
        return new ResponseEntity<>(incorrectData, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<IncorrectData> handleAccessDeniedException() {
        IncorrectData incorrectData = new IncorrectData("Access denied");
        return new ResponseEntity<>(incorrectData, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed.");
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        exceptionBody.setErrors(errors.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        return exceptionBody;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<IncorrectData> handleAuthenticationException(AuthenticationException e) {
        IncorrectData incorrectData = new IncorrectData("Authentication failed!");
        return new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<IncorrectData> handleException(Exception e) {
        IncorrectData addressIncorrectData = new IncorrectData(e.getMessage());
        return new ResponseEntity<>(addressIncorrectData, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
