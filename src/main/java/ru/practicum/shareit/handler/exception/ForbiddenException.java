package ru.practicum.shareit.handler.exception;

public class ForbiddenException extends RuntimeException {
    private final String message;

    public ForbiddenException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
