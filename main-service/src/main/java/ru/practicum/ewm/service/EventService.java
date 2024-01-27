package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {

    List<EventFullDto> getAllEventFromAdmin(SearchEventParamsAdminDto searchEventParamsAdminDto);

    EventFullDto updateEventFromAdmin(Long eventId, UpdateEventAdminRequestDtoDto inputUpdate);

    List<EventShortDto> getEventsByUserId(Long userId, Integer from, Integer size);

    EventFullDto addNewEvent(Long userId, NewEventDto input);

    EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId);

    EventFullDto updateEventByUserIdAndEventId(Long userId, Long eventId, UpdateEventUserRequestDto inputUpdate);

    List<ParticipationRequestDto> getAllParticipationRequestsFromEventByOwner(Long userId, Long eventId);

    EventRequestStatusUpdateResultDto updateStatusRequest(Long userId, Long eventId, EventRequestStatusUpdateRequestDto inputUpdate);

    List<EventShortDto> getAllEventFromPublic(SearchEventParamsDto searchEventParamsDto, HttpServletRequest request);

    EventFullDto getEventById(Long eventId, HttpServletRequest request);
}