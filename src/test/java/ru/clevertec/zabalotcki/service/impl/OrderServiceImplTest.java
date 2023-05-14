package ru.clevertec.zabalotcki.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.zabalotcki.builder.GiftCertificateBuilder;
import ru.clevertec.zabalotcki.builder.OrderBuilder;
import ru.clevertec.zabalotcki.builder.TestBuilder;
import ru.clevertec.zabalotcki.builder.UserBuilder;
import ru.clevertec.zabalotcki.builder.dto.GiftCertificateDtoBuilder;
import ru.clevertec.zabalotcki.builder.dto.OrderDtoBuilder;
import ru.clevertec.zabalotcki.builder.dto.UserDtoBuilder;
import ru.clevertec.zabalotcki.dao.OrderRepository;
import ru.clevertec.zabalotcki.dto.GiftCertificateDto;
import ru.clevertec.zabalotcki.dto.OrderDto;
import ru.clevertec.zabalotcki.dto.OrderInfoDto;
import ru.clevertec.zabalotcki.dto.UserDto;
import ru.clevertec.zabalotcki.mapper.OrderMapper;
import ru.clevertec.zabalotcki.model.GiftCertificate;
import ru.clevertec.zabalotcki.model.Order;
import ru.clevertec.zabalotcki.model.User;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl service;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private OrderRepository repository;

    @Mock
    private OrderMapper mapper;

    private static Order order;
    private static OrderDto orderDto;

    private static User user;
    private static UserDto userDto;

    private static GiftCertificate certificate;
    private static GiftCertificateDto certificateDto;

    @BeforeEach
    void setUp() {
        TestBuilder<Order> testBuilder1 = new OrderBuilder();
        TestBuilder<OrderDto> testBuilder2 = new OrderDtoBuilder();
        order = testBuilder1.build();
        orderDto = testBuilder2.build();

        TestBuilder<User> userTestBuilder = new UserBuilder();
        TestBuilder<UserDto> userDtoBuilder = new UserDtoBuilder();
        user = userTestBuilder.build();
        userDto = userDtoBuilder.build();

        TestBuilder<GiftCertificate> testBuilderCertificate = new GiftCertificateBuilder();
        TestBuilder<GiftCertificateDto> testBuilderCertificateDto = new GiftCertificateDtoBuilder();
        certificate = testBuilderCertificate.build();
        certificateDto = testBuilderCertificateDto.build();
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
    void checkFindAllShouldReturnAllOrders() {
        List<Order> expectedList = new ArrayList<>(Collections.singletonList(order));
        List<OrderDto> expectedListDto = new ArrayList<>(Collections.singletonList(orderDto));

        when(repository.findAll()).thenReturn(expectedList);
        when(mapper.toDtoList(expectedList)).thenReturn(expectedListDto);

        List<OrderDto> actualList = service.findAll();

        assertNotNull(actualList);

        verify(repository, times(1)).findAll();
    }

    @ParameterizedTest
    @CsvSource({"1,1"})
    void checkCreateOrderShouldSaveAndReturnSavedOrder(Long userId, Long certId) {
        when(giftCertificateService.findById(certId)).thenReturn(certificateDto);
        when(userService.findByIdEntity(userId)).thenReturn(order.getUser());
        when(giftCertificateService.findByIdEntity(certId)).thenReturn(order.getGiftCertificate());
        lenient().when(repository.save(order)).thenReturn(order);
        lenient().when(mapper.toDto(order)).thenReturn(orderDto);
        when(service.createOrder(userId, certId)).thenReturn(orderDto);

        OrderDto result = service.createOrder(order.getUser().getId(), order.getGiftCertificate().getId());

        assertEquals(orderDto, result);
    }

    @ParameterizedTest
    @MethodSource("params")
    void checkFindByIdShouldReturnExpectedOrder(Long id) {
        when(repository.findById(id)).thenReturn(Optional.of(order));
        when(mapper.toDto(order)).thenReturn(orderDto);

        OrderDto actual = service.findById(id);

        assertNotNull(actual);

        verify(repository, times(1)).findById(id);
    }

    @ParameterizedTest
    @MethodSource("params")
    void checkDeleteByIdShouldDeleteOrderA(Long id) {
        doNothing().when(repository).deleteById(id);

        service.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }

    @ParameterizedTest
    @MethodSource("params")
    void checkFindByUserShouldReturnUsersOrders(Long id) {

        List<Order> orders = new ArrayList<>();
        orders.add(order);

        List<OrderDto> orderDtos = new ArrayList<>();
        orderDtos.add(orderDto);

        when(repository.findByUser(user)).thenReturn(orders);
        when(userService.findByIdEntity(id)).thenReturn(user);
        when(mapper.toDtoList(orders)).thenReturn(orderDtos);

        List<OrderDto> result = service.findByUser(id);

        assertEquals(orderDtos, result);
    }

    @ParameterizedTest
    @MethodSource("params")
    void checkFindInfoShouldReturnExpectedCostAndDate(Long id) {
        when(repository.findById(id)).thenReturn(Optional.ofNullable(order));
        when(service.findById(id)).thenReturn(orderDto);

        OrderInfoDto expected = OrderInfoDto.builder()
                .cost(order.getCost())
                .purchaseDate(order.getPurchaseDate())
                .build();
        OrderInfoDto actual = service.findInfo(id);

        assertEquals(expected.getCost(), actual.getCost());
        assertEquals(expected.getPurchaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                actual.getPurchaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        verify(repository, times(2)).findById(id);

    }
}
