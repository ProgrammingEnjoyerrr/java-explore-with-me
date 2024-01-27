package ru.practicum.ewm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.exception.BaseErrorHandler;
import ru.practicum.ewm.exception.ErrorResponse;
import ru.practicum.ewm.exception.NotFoundException;

@RestControllerAdvice(value = "ru.practicum.ewm.controller")
public class StatsErrorHandler extends BaseErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        return commonErrorResponse(e, HttpStatus.NOT_FOUND);
    }
}
