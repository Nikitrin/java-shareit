package ru.practicum.shareit.handler.exception;

public class UnsupportedStatusException extends RuntimeException {
    private final String message;

    public UnsupportedStatusException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
