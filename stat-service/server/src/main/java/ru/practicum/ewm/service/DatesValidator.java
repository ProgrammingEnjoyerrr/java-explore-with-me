package ru.practicum.ewm.service;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.ewm.exception.InvalidDatesException;

import java.time.LocalDateTime;

@UtilityClass
@Slf4j
public class DatesValidator {
    public void validate(final LocalDateTime start, final LocalDateTime end) {
        if (end.isBefore(start)) {
            final String message = String.format("Incorrect format of dates start %s and end %s", start, end);
            log.info(message);
            throw new InvalidDatesException(message);
        }
    }
}
