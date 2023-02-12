package ru.practicum.shareit.handler.exception;

public class NotFoundException extends RuntimeException {
    private String message;

    public NotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public NotFoundException() {
    }

    public String getMessage() {
        return message;
    }
}
