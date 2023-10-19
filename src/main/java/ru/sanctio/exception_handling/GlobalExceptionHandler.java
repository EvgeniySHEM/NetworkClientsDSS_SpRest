package ru.sanctio.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(NoSuchDataException e) {
        IncorrectData addressIncorrectData = new IncorrectData(e.getMessage());
        return new ResponseEntity<>(addressIncorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(Exception e) {
        IncorrectData addressIncorrectData = new IncorrectData(e.getMessage());
        return new ResponseEntity<>(addressIncorrectData, HttpStatus.BAD_REQUEST);
    }
}
