package ru.practicum.shareit.handler.exception;

public class NotFoundException extends RuntimeException {
    private final String message;

    public NotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}