package ru.clevertec.zabalotcki.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.zabalotcki.builder.OrderBuilder;
import ru.clevertec.zabalotcki.builder.TestBuilder;
import ru.clevertec.zabalotcki.builder.dto.OrderDtoBuilder;
import ru.clevertec.zabalotcki.dto.OrderDto;
import ru.clevertec.zabalotcki.model.Order;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderMapperTest {


    @Mock
    private UserMapper userMapper;

    @Mock
    private GiftCertificateMapper certificateMapper;

    @InjectMocks
    private OrderMapper orderMapper;

    private static Order order;
    private static OrderDto orderDto;

    @BeforeEach
    void setUp() {
        TestBuilder<Order> testBuilder1 = new OrderBuilder();
        TestBuilder<OrderDto> testBuilder2 = new OrderDtoBuilder();
        order = testBuilder1.build();
        orderDto = testBuilder2.build();
    }

    @Test
    void checkToEntityShouldConvertDtoToEntity() {
        order.setId(null);

        when(userMapper.toEntity(orderDto.getUser())).thenReturn(order.getUser());
        when(certificateMapper.toEntity(orderDto.getGiftCertificates())).thenReturn(order.getGiftCertificate());

        Order actual = orderMapper.toEntity(orderDto);

        assertEquals(order.getUser(), actual.getUser());
        assertEquals(order.getCost(), actual.getCost());
        assertEquals(order.getGiftCertificate(), actual.getGiftCertificate());
        assertEquals(order.getPurchaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                actual.getPurchaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    @Test
    void checkToDtoShouldConvertEntityToDto() {
        orderDto.setId(null);

        when(userMapper.toDto(order.getUser())).thenReturn(orderDto.getUser());
        when(certificateMapper.toDto(order.getGiftCertificate())).thenReturn(orderDto.getGiftCertificates());

        OrderDto actual = orderMapper.toDto(order);

        assertEquals(orderDto.getUser(), actual.getUser());
        assertEquals(orderDto.getCost(), actual.getCost());
        assertEquals(orderDto.getGiftCertificates(), actual.getGiftCertificates());
        assertEquals(orderDto.getPurchaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                actual.getPurchaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    @Test
    void checkToDtoListShouldReturnEmptyListWhenGivenNull() {
        List<OrderDto> result = orderMapper.toDtoList(null);

        assertNull(result);
    }

    @Test
    void checkToDtoListShouldReturnConvertedList() {
        List<Order> orders = Collections.singletonList(order);

        when(userMapper.toDto(any())).thenReturn(orderDto.getUser());
        when(certificateMapper.toDto(any())).thenReturn(orderDto.getGiftCertificates());

        List<OrderDto> result = orderMapper.toDtoList(orders);

        assertEquals(orders.size(), result.size());

    }
}
