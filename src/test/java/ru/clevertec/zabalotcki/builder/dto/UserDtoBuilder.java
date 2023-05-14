package ru.clevertec.zabalotcki.builder.dto;

import ru.clevertec.zabalotcki.builder.TestBuilder;
import ru.clevertec.zabalotcki.dto.UserDto;

public class UserDtoBuilder implements TestBuilder<UserDto> {

    private Long id = 1L;
    private String name = "User";

    @Override
    public UserDto build() {
        return UserDto.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
