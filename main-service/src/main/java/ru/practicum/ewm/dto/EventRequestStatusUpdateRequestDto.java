package ru.practicum.ewm.dto;

import lombok.*;
import ru.practicum.ewm.model.enums.RequestStatus;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRequestStatusUpdateRequestDto {
    private Set<Long> requestIds;
    private RequestStatus status;
}