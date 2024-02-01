package ru.practicum.ewm.controller;

import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.IncorrectParametersException;
import ru.practicum.ewm.exception.NotFoundException;

@RestControllerAdvice
public class CommonControllerAdvice extends BaseErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        return commonErrorResponse(e, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({IncorrectParametersException.class, MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerIncorrectParametersException(Exception e) {
        return commonErrorResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PSQLException.class, ConflictException.class, DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerValidationException(Exception e) {
        return commonErrorResponse(e, HttpStatus.CONFLICT);
    }
}