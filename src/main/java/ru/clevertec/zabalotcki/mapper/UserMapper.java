package ru.clevertec.zabalotcki.mapper;

import org.springframework.stereotype.Component;
import ru.clevertec.zabalotcki.dto.UserDto;
import ru.clevertec.zabalotcki.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public User toEntity(UserDto dto) {
        return User.builder()
                .name(dto.getName())
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .build();
    }

    public List<UserDto> toListDto(List<User> users) {
        if (users == null) {
            return null;
        }

        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(toDto(user));
        }
        return userDtos;
    }
}
