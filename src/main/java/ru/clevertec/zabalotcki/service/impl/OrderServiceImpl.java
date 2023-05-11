package ru.clevertec.zabalotcki.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.zabalotcki.dao.OrderRepository;
import ru.clevertec.zabalotcki.dto.GiftCertificateDto;
import ru.clevertec.zabalotcki.dto.OrderDto;
import ru.clevertec.zabalotcki.dto.OrderInfoDto;
import ru.clevertec.zabalotcki.exception.OrderNotFoundException;
import ru.clevertec.zabalotcki.mapper.OrderMapper;
import ru.clevertec.zabalotcki.model.Order;
import ru.clevertec.zabalotcki.service.GiftCertificateService;
import ru.clevertec.zabalotcki.service.OrderService;
import ru.clevertec.zabalotcki.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final UserService userService;
    private final GiftCertificateService giftCertificateService;

    @Override
    public List<OrderDto> findAll() {
        return orderMapper.toDtoList(orderRepository.findAll());
    }

    @Override
    public OrderDto createOrder(Long userId, Long certId) {
        GiftCertificateDto certificateById = giftCertificateService.findById(certId);
        BigDecimal price = certificateById.getPrice();

        Order order = new Order();

        order.setUser(userService.findByIdEntity(userId));
        order.setGiftCertificate(giftCertificateService.findByIdEntity(certId));
        order.setCost(price);
        order.setPurchaseDate(LocalDateTime.now());

        Order saved = orderRepository.save(order);
        return orderMapper.toDto(saved);
    }

    @Override
    public OrderDto findById(Long id) {
        return Optional.ofNullable(orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Check order id " + id)))
                .map(orderMapper::toDto)
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderDto> findByUser(Long userId) {
        List<Order> byUser = orderRepository.findByUser(userService.findByIdEntity(userId));
        return orderMapper.toDtoList(byUser);
    }

    @Override
    public OrderInfoDto findInfo(Long orderId) {
        OrderDto byId = findById(orderId);
        return toOrderInfoDto(byId);
    }

    private OrderInfoDto toOrderInfoDto(OrderDto order) {
        return OrderInfoDto.builder()
                .cost(order.getCost())
                .purchaseDate(order.getPurchaseDate())
                .build();
    }
}
