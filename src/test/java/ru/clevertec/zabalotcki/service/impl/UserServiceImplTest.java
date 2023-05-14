package ru.clevertec.zabalotcki.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.zabalotcki.builder.OrderBuilder;
import ru.clevertec.zabalotcki.builder.TestBuilder;
import ru.clevertec.zabalotcki.builder.UserBuilder;
import ru.clevertec.zabalotcki.builder.dto.OrderDtoBuilder;
import ru.clevertec.zabalotcki.builder.dto.UserDtoBuilder;
import ru.clevertec.zabalotcki.dao.UserRepository;
import ru.clevertec.zabalotcki.dto.OrderDto;
import ru.clevertec.zabalotcki.dto.TagDto;
import ru.clevertec.zabalotcki.dto.UserDto;
import ru.clevertec.zabalotcki.exception.UserNotFoundException;
import ru.clevertec.zabalotcki.mapper.UserMapper;
import ru.clevertec.zabalotcki.model.Order;
import ru.clevertec.zabalotcki.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    private static User user;
    private static UserDto userDto;
    private static Order order;
    private static OrderDto orderDto;

    @BeforeEach
    void setUp() {
        TestBuilder<User> testBuilder1 = new UserBuilder();
        TestBuilder<UserDto> testBuilder2 = new UserDtoBuilder();
        user = testBuilder1.build();
        userDto = testBuilder2.build();

        TestBuilder<Order> testBuilderOrder = new OrderBuilder();
        TestBuilder<OrderDto> testBuilderOrderDto = new OrderDtoBuilder();
        order = testBuilderOrder.build();
        orderDto = testBuilderOrderDto.build();
    }

    static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of(1L),
                Arguments.of(2L),
                Arguments.of(3L),
                Arguments.of(4L)
        );
    }

    @Test
    void checkFindAllShouldReturnAllUsers() {
        List<User> expectedList = new ArrayList<>(Collections.singletonList(user));
        List<UserDto> expectedListDto = new ArrayList<>(Collections.singletonList(userDto));

        when(repository.findAll()).thenReturn(expectedList);
        when(mapper.toListDto(expectedList)).thenReturn(expectedListDto);

        List<UserDto> actualList = service.findAll();

        assertNotNull(actualList);

        verify(repository, times(1)).findAll();
    }

    @ParameterizedTest
    @MethodSource("params")
    void checkFindByIdShouldReturnNotNullUser(Long id) {
        when(repository.findById(id)).thenReturn(Optional.of(user));
        when(mapper.toDto(user)).thenReturn(userDto);

        UserDto actual = service.findById(id);

        assertNotNull(actual);

        verify(repository, times(1)).findById(id);
    }

    @ParameterizedTest
    @MethodSource("params")
    public void checkFindByIdShouldThrowUserNotFoundException(Long id) {
        when(repository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            service.findById(id);
        });
    }

    @Test
    void checkSaveShouldReturnNotNullUser() {
        when(mapper.toEntity(userDto)).thenReturn(user);
        when(repository.save(user)).thenReturn(user);
        when(mapper.toDto(user)).thenReturn(userDto);

        UserDto actual = service.save(userDto);

        assertNotNull(actual);
        verify(repository, times(1)).save(user);
    }

    @ParameterizedTest
    @MethodSource("params")
    void checkDeleteByIdShouldDeleteUser(Long id) {
        doNothing().when(repository).deleteById(id);

        service.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }

    @ParameterizedTest
    @MethodSource("params")
    void checkFindByTagShouldReturnExpectedTag(Long id) {
        List<TagDto> tags = orderDto.getGiftCertificates().getTags();
        List<String> names = tags.stream().map(TagDto::getName).collect(Collectors.toList());
        when(repository.findTag(id)).thenReturn(names);

        String actualTag = service.findTag(id);

        assertEquals(names.get(0), actualTag);
    }

    @ParameterizedTest
    @MethodSource("params")
    void checkFindByIdEntityShouldReturnNotNullEntity(Long id) {
        when(repository.findById(id)).thenReturn(java.util.Optional.ofNullable(user));

        User actual = service.findByIdEntity(id);

        assertNotNull(actual);

        verify(repository, times(1)).findById(id);
    }
}
