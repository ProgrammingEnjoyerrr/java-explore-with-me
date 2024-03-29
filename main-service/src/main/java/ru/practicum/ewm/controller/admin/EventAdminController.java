package ru.practicum.ewm.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.SearchEventParamsAdminDto;
import ru.practicum.ewm.dto.UpdateEventAdminRequestDtoDto;
import ru.practicum.ewm.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
@Validated
@Slf4j
public class EventAdminController {
    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> searchEvents(@Valid SearchEventParamsAdminDto searchEventParamsAdminDto) {
        log.info("GET запрос на получение списка событий");
        return eventService.getAllEventFromAdmin(searchEventParamsAdminDto);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventByAdmin(@PathVariable(value = "eventId") @Min(1) Long eventId,
                                           @RequestBody @Valid UpdateEventAdminRequestDtoDto inputUpdate) {
        log.info("PATCH запрос на обновление списка событий");
        return eventService.updateEventFromAdmin(eventId, inputUpdate);
    }
}