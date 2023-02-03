package ru.practicum.shareit.handler.exception;

public class AvailableException extends RuntimeException {
    private final String message;

    public AvailableException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

