package ru.clevertec.zabalotcki.service;

import ru.clevertec.zabalotcki.dto.UserDto;
import ru.clevertec.zabalotcki.model.User;

import java.util.List;

public interface UserService {
    UserDto findById(Long id);

    List<UserDto> findAll();

    UserDto save(UserDto userDto);

    void deleteById(Long id);

    User findByIdEntity(Long id);

    String findTag(Long userId);
}
