package ru.clevertec.zabalotcki.builder.dto;

import ru.clevertec.zabalotcki.builder.TestBuilder;
import ru.clevertec.zabalotcki.dto.GiftCertificateDto;
import ru.clevertec.zabalotcki.dto.OrderDto;
import ru.clevertec.zabalotcki.dto.UserDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDtoBuilder implements TestBuilder<OrderDto> {

    private Long id = 1L;
    private BigDecimal cost = new BigDecimal("12.00");
    private LocalDateTime purchaseDate = LocalDateTime.now();
    private UserDto user = new UserDtoBuilder().build();
    private GiftCertificateDto giftCertificate = new GiftCertificateDtoBuilder().build();

    @Override
    public OrderDto build() {
        return OrderDto.builder()
                .id(this.id)
                .cost(this.cost)
                .purchaseDate(this.purchaseDate)
                .user(this.user)
                .giftCertificates(this.giftCertificate)
                .build();
    }
}
