package ru.clevertec.zabalotcki.service;

import ru.clevertec.zabalotcki.dto.OrderDto;
import ru.clevertec.zabalotcki.dto.OrderInfoDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> findAll();

    OrderDto createOrder(Long userId, Long certId);

    OrderDto findById(Long id);

    void deleteById(Long id);

    List<OrderDto> findByUser(Long userId);

    OrderInfoDto findInfo(Long orderId);
}
