package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.NewUserRequestDto;
import ru.practicum.ewm.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto addNewUser(NewUserRequestDto newUserRequestDto);

    void deleteUser(Long userId);

    List<UserDto> getListUsers(List<Long> ids, Integer from, Integer size);
}