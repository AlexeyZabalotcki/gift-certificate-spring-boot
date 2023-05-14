package ru.clevertec.zabalotcki.integration.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.zabalotcki.dto.UserDto;
import ru.clevertec.zabalotcki.exception.UserNotFoundException;
import ru.clevertec.zabalotcki.integration.BaseIntegrationTest;
import ru.clevertec.zabalotcki.model.User;
import ru.clevertec.zabalotcki.service.impl.UserServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceImplIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private UserServiceImpl service;

    @Test
    void checkFindAllShouldReturnAllUsers() {
        List<UserDto> actualUsers = service.findAll();
        assertEquals(3, actualUsers.size());
    }

    @ParameterizedTest
    @ValueSource(longs = 1L)
    void checkFindByIdShouldReturnExpectedUser(Long id) {
        UserDto expectedAndy = UserDto.builder()
                .name("Andy")
                .build();
        UserDto actual = service.findById(id);
        assertEquals(expectedAndy, actual);
    }

    @Test
    @Transactional
    void checkSaveShouldSaveNewUser() {
        UserDto expectedSave = UserDto.builder()
                .name("Nick")
                .build();
        service.save(expectedSave);
        UserDto actual = service.findById(4L);
        assertEquals(expectedSave, actual);
    }

    @Test
    @Transactional
    void checkSaveShouldReturnSavedUser() {
        UserDto expectedSave = UserDto.builder()
                .name("Nick")
                .build();
        UserDto actualSaved = service.save(expectedSave);
        assertEquals(expectedSave, actualSaved);
    }

    @Transactional
    @ParameterizedTest
    @ValueSource(longs = 1L)
    void checkDeleteByIdShouldDeleteUserAndThrowsExceptionWhenFindThisUser(Long id) {
        service.deleteById(id);
        assertThrows(UserNotFoundException.class, () -> {
            service.findById(id);
        });
    }

    @ParameterizedTest
    @ValueSource(longs = 1L)
    void checkFindByTagShouldReturnTag2(Long id) {
        String tag = service.findTag(id);
        assertEquals("tag 2", tag);
    }

    @ParameterizedTest
    @ValueSource(longs = 1L)
    void checkFindByIdEntityShouldReturnExpectedEntity(Long id) {
        User expected = User.builder().name("Andy").build();
        User actual = service.findByIdEntity(id);
        assertEquals(expected.equals(actual), actual.equals(expected));
    }
}
