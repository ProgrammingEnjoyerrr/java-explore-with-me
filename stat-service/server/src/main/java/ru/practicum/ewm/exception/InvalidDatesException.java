package ru.practicum.ewm.exception;

public class InvalidDatesException extends RuntimeException {
    public InvalidDatesException(final String message) {
        super(message);
    }
}
