package ru.sanctio.exception_handling;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionBodyTest {

    @Test
    public void testGetMessage() {
        String message = "Test Message";
        ExceptionBody exceptionBody = new ExceptionBody(message);
        assertEquals(message, exceptionBody.getMessage());
    }

    @Test
    public void testSetMessage() {
        String message = "Test Message";
        ExceptionBody exceptionBody = new ExceptionBody("");
        exceptionBody.setMessage(message);
        assertEquals(message, exceptionBody.getMessage());
    }

    @Test
    public void testGetErrors() {
        Map<String, String> errors = new HashMap<>();
        errors.put("key1", "value1");
        errors.put("key2", "value2");
        ExceptionBody exceptionBody = new ExceptionBody("", errors);
        assertEquals(errors, exceptionBody.getErrors());
    }

    @Test
    public void testSetErrors() {
        Map<String, String> errors = new HashMap<>();
        errors.put("key1", "value1");
        ExceptionBody exceptionBody = new ExceptionBody("");
        exceptionBody.setErrors(errors);
        assertEquals(errors, exceptionBody.getErrors());
    }


}