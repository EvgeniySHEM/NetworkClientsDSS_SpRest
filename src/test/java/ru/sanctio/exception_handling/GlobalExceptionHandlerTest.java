package ru.sanctio.exception_handling;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.sanctio.models.entity.Address;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private static GlobalExceptionHandler globalExceptionHandler;

    @BeforeAll
    static void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void testHandleNoSuchDataException() {
        NoSuchDataException e = new NoSuchDataException("Data not found");

        ResponseEntity<IncorrectData> responseEntity = globalExceptionHandler.handleNoSuchDataException(e);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Data not found",
                Objects.requireNonNull(responseEntity.getBody()).getErrorInfo());
    }

    @Test
    public void testHandleRepeatingAddressException() {
        RepeatingAddressException e = new RepeatingAddressException("Duplicate addresses in the database");

        ResponseEntity<IncorrectData> responseEntity = globalExceptionHandler.handleRepeatingAddressException(e);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("Duplicate addresses in the database",
                Objects.requireNonNull(responseEntity.getBody()).getErrorInfo());
    }

    @Test
    public void testHandleIllegalArgumentException() {
        IllegalArgumentException e = new IllegalArgumentException("IllegalArgument");

        ResponseEntity<IncorrectData> responseEntity = globalExceptionHandler.handleIllegalArgumentException(e);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("IllegalArgument",
                Objects.requireNonNull(responseEntity.getBody()).getErrorInfo());
    }

    @Test
    public void testHandleIllegalStateException() {
        IllegalStateException e = new IllegalStateException("IllegalStateException");

        ResponseEntity<IncorrectData> responseEntity =
                globalExceptionHandler.handleIllegalStateException(e);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("IllegalStateException",
                Objects.requireNonNull(responseEntity.getBody()).getErrorInfo());
    }

    @Test
    public void testHandleAccessDeniedException() {
        AccessDeniedException e = new AccessDeniedException("");

        ResponseEntity<IncorrectData> responseEntity =
                globalExceptionHandler.handleAccessDeniedException();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("Access denied",
                Objects.requireNonNull(responseEntity.getBody()).getErrorInfo());
    }

    private void testMetod(String string, Integer integer) {
    }

    @Test
    public void testHandleMethodArgumentNotValidException() throws NoSuchMethodException {
        MutablePropertyValues mpv = new MutablePropertyValues();
        mpv.add("ip", 1);
        mpv.add("mac", 1);
        mpv.add("model", 1);
        mpv.add("address", 1);

        Address address = new Address();
        DataBinder db = new DataBinder(address);
        db.bind(mpv);
        MethodArgumentNotValidException e = new MethodArgumentNotValidException(new MethodParameter(
                this.getClass().getDeclaredMethod(
                        "testMetod", String.class, Integer.class),
                0), db.getBindingResult());

        Map<String, String> mapErrors = db.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        ExceptionBody exceptionBody = globalExceptionHandler.handleMethodArgumentNotValidException(e);

        assertEquals(mapErrors, exceptionBody.getErrors());
    }

    @Test
    public void testHandleAuthenticationException() {

        AuthenticationException ex = new AuthenticationServiceException("");

        ResponseEntity<IncorrectData> responseEntity =
                globalExceptionHandler.handleAuthenticationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Authentication failed!",
                Objects.requireNonNull(responseEntity.getBody()).getErrorInfo());
    }

    @Test
    public void testHandleException() {
        Exception e = new Exception("Exception");

        ResponseEntity<IncorrectData> responseEntity = globalExceptionHandler.handleException(e);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Exception",
                Objects.requireNonNull(responseEntity.getBody()).getErrorInfo());
    }


}