package ru.practicum.shareit.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.w3c.dom.ranges.RangeException;
import ru.practicum.shareit.handler.exception.ForbiddenException;
import ru.practicum.shareit.handler.exception.NotFoundException;
import ru.practicum.shareit.handler.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String PATH = "path";
    private static final String REASONS = "reasons";


    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(NotFoundException ex, WebRequest request) {
        log.error("Not found error: {}", ex.getMessage());
        Map<String, Object> body = getGeneralErrorBody(HttpStatus.NOT_FOUND, request);
        body.put(REASONS, ex.getMessage());
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = ValidationException.class)
    protected ResponseEntity<Object> handleInvalidInput(ValidationException ex, WebRequest request) {
        log.error("Validation error: {}", ex.getMessage());
        Map<String, Object> body = getGeneralErrorBody(HttpStatus.CONFLICT, request);
        body.put(REASONS, ex.getMessage());
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = MissingRequestHeaderException.class)
    protected ResponseEntity<Object> handlerMissingHeader(MissingRequestHeaderException ex, WebRequest request) {
        log.error("Missing request header: {}", ex.getMessage());
        Map<String, Object> body = getGeneralErrorBody(HttpStatus.BAD_REQUEST, request);
        body.put(REASONS, ex.getMessage());
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = ForbiddenException.class)
    protected ResponseEntity<Object> handlerForbidden(ForbiddenException ex, WebRequest request) {
        log.error("Forbidden: {}", ex.getMessage());
        Map<String, Object> body = getGeneralErrorBody(HttpStatus.FORBIDDEN, request);
        body.put(REASONS, ex.getMessage());
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = Throwable.class)
    protected ResponseEntity<Object> otherHandler(Exception ex, WebRequest request) {
        log.error("I’m a teapot: {}", ex.getMessage());
        Map<String, Object> body = getGeneralErrorBody(HttpStatus.I_AM_A_TEAPOT, request);
        body.put(REASONS, ex.getMessage());
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.I_AM_A_TEAPOT, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.info("Not Valid. Message: {}", ex.getMessage());
        Map<String, Object> body = getGeneralErrorBody(status, request);
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(this::getErrorString)
                .collect(Collectors.toList());
        body.put(REASONS, errors);
        return new ResponseEntity<>(body, headers, status);
    }

    private Map<String, Object> getGeneralErrorBody(HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, OffsetDateTime.now());
        body.put(STATUS, status.value());
        body.put(ERROR, status.getReasonPhrase());
        body.put(PATH, getRequestUrl(request));
        return body;
    }

    private String getErrorString(ObjectError error) {
        if (error instanceof FieldError) {
            return ((FieldError) error).getField() + " " + error.getDefaultMessage();
        }
        return error.getDefaultMessage();
    }

    private String getRequestUrl(WebRequest request) {
        if (request instanceof ServletWebRequest) {
            HttpServletRequest requestHttp = ((ServletWebRequest) request).getRequest();
            return String.format("%s, %s", requestHttp.getMethod(), requestHttp.getRequestURL());
        }
        return "";
    }
}
