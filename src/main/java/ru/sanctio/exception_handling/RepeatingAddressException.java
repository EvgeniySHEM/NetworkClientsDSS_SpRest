package ru.sanctio.exception_handling;

public class RepeatingAddressException extends RuntimeException{

    public RepeatingAddressException(String message) {
        super(message);
    }
}
