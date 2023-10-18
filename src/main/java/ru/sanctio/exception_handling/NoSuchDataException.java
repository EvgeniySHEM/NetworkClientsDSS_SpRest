package ru.sanctio.exception_handling;

public class NoSuchDataException extends RuntimeException{

    public NoSuchDataException(String message) {
        super(message);
    }
}
