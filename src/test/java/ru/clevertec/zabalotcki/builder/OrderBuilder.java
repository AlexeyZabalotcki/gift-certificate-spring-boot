package ru.clevertec.zabalotcki.builder;

import ru.clevertec.zabalotcki.model.GiftCertificate;
import ru.clevertec.zabalotcki.model.Order;
import ru.clevertec.zabalotcki.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderBuilder implements TestBuilder<Order> {

    private Long id = 1L;
    private BigDecimal cost = new BigDecimal("12.00");
    private LocalDateTime purchaseDate = LocalDateTime.now();
    private User user = new UserBuilder().build();
    private GiftCertificate giftCertificate = new GiftCertificateBuilder().build();

    @Override
    public Order build() {
        return Order.builder()
                .id(this.id)
                .cost(this.cost)
                .purchaseDate(LocalDateTime.from(this.purchaseDate))
                .user(this.user)
                .giftCertificate(this.giftCertificate)
                .build();
    }
}
