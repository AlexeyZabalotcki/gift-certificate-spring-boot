package ru.clevertec.zabalotcki.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.zabalotcki.builder.TestBuilder;
import ru.clevertec.zabalotcki.builder.UserBuilder;
import ru.clevertec.zabalotcki.builder.dto.UserDtoBuilder;
import ru.clevertec.zabalotcki.dto.UserDto;
import ru.clevertec.zabalotcki.model.User;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    private static User user;
    private static UserDto userDto;

    @BeforeEach
    void setUp() {
        TestBuilder<User> testBuilder1 = new UserBuilder();
        TestBuilder<UserDto> testBuilder2 = new UserDtoBuilder();
        user = testBuilder1.build();
        userDto = testBuilder2.build();
    }

    @Test
    void checkToEntityShouldConvertDtoToEntity() {
        user.setId(null);
        User actualUser = userMapper.toEntity(userDto);
        assertEquals(user, actualUser);
    }

    @Test
    void checkToDtoShouldConvertEntityToDto() {
        userDto.setId(null);
        UserDto actualUser = userMapper.toDto(user);
        assertEquals(userDto, actualUser);
    }

    @Test
    void checkToListDtoShouldReturnEmptyListWhenGivenNull() {
        List<UserDto> result = userMapper.toListDto(null);

        assertNull(result);
    }

    @Test
    void checkToListDtoShouldReturnConvertedList() {
        List<User> users = Collections.singletonList(user);

        List<UserDto> result = userMapper.toListDto(users);

        assertEquals(users.size(), result.size());
    }
}
