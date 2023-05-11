package ru.clevertec.zabalotcki.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.zabalotcki.dto.OrderDto;
import ru.clevertec.zabalotcki.model.Order;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final UserMapper userMapper;
    private final GiftCertificateMapper giftCertificateMapper;

    public Order toEntity(OrderDto dto) {
        return Order.builder()
                .cost(dto.getCost())
                .purchaseDate(dto.getPurchaseDate())
                .user(userMapper.toEntity(dto.getUser()))
                .giftCertificate(giftCertificateMapper.toEntity(dto.getGiftCertificates()))
                .build();
    }

    public OrderDto toDto(Order order) {
        return OrderDto.builder()
                .cost(order.getCost())
                .purchaseDate(order.getPurchaseDate())
                .user(userMapper.toDto(order.getUser()))
                .giftCertificates(giftCertificateMapper.toDto(order.getGiftCertificate()))
                .build();
    }

    public List<OrderDto> toDtoList(List<Order> orders) {
        if (orders == null) {
            return null;
        }
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            orderDtos.add(toDto(order));
        }
        return orderDtos;
    }

    public List<Order> toEntityList(List<OrderDto> orders) {
        if (orders == null) {
            return null;
        }
        List<Order> orderList = new ArrayList<>();
        for (OrderDto order : orders) {
            orderList.add(toEntity(order));
        }
        return orderList;
    }
}
