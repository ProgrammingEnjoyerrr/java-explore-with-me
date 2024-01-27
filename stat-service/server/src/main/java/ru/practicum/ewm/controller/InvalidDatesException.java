package ru.practicum.ewm.controller;

public class InvalidDatesException extends RuntimeException {
    public InvalidDatesException(final String message) {
        super(message);
    }
}
