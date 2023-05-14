package ru.clevertec.zabalotcki.integration.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.zabalotcki.dto.*;
import ru.clevertec.zabalotcki.exception.OrderNotFoundException;
import ru.clevertec.zabalotcki.integration.BaseIntegrationTest;
import ru.clevertec.zabalotcki.service.impl.OrderServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderServiceImplIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private OrderServiceImpl service;

    @Test
    void checkFindAllShouldReturnAllOrders() {
        List<OrderDto> actual = service.findAll();
        assertEquals(4, actual.size());
    }

    @Transactional
    @ParameterizedTest
    @CsvSource({"2,1"})
    void checkCreateOrderShouldSaveAndReturnSavedOrder(Long userId, Long certId) {
        OrderDto expected = getOrderDto();
        OrderDto actual = service.createOrder(userId, certId);

        //assert Orders
        assertEquals(expected.getCost(), actual.getCost());
        assertEquals(expected.getPurchaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                actual.getPurchaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        assertEquals(expected.getUser(), actual.getUser());
        //assert Gift Certificates
        assertEquals(expected.getGiftCertificates().getName(), actual.getGiftCertificates().getName());
        assertEquals(expected.getGiftCertificates().getDescription(), actual.getGiftCertificates().getDescription());
        assertEquals(expected.getGiftCertificates().getDuration(), actual.getGiftCertificates().getDuration());
        assertEquals(expected.getGiftCertificates().getPrice().floatValue(), actual.getGiftCertificates().getPrice().floatValue());
        assertEquals(expected.getGiftCertificates().getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                actual.getGiftCertificates().getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        assertEquals(expected.getGiftCertificates().getLastUpdateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                actual.getGiftCertificates().getLastUpdateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        assertEquals(expected.getGiftCertificates().getTags(), actual.getGiftCertificates().getTags());
    }

    @ParameterizedTest
    @ValueSource(longs = 1L)
    void checkFindByIdShouldReturnExpectedOrder(Long id) {
        OrderDto expected = getOrderDto();
        expected.setPurchaseDate(LocalDateTime.of(2023, 5, 11, 8, 13));
        OrderDto actual = service.findById(id);

        assertEquals(expected.getCost(), actual.getCost());
        assertEquals(expected.getPurchaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                actual.getPurchaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        assertEquals(expected.getUser(), actual.getUser());

        assertEquals(expected.getGiftCertificates().getName(), actual.getGiftCertificates().getName());
        assertEquals(expected.getGiftCertificates().getDescription(), actual.getGiftCertificates().getDescription());
        assertEquals(expected.getGiftCertificates().getDuration(), actual.getGiftCertificates().getDuration());
        assertEquals(expected.getGiftCertificates().getPrice().floatValue(), actual.getGiftCertificates().getPrice().floatValue());
        assertEquals(expected.getGiftCertificates().getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                actual.getGiftCertificates().getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        assertEquals(expected.getGiftCertificates().getLastUpdateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                actual.getGiftCertificates().getLastUpdateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        assertEquals(expected.getGiftCertificates().getTags(), actual.getGiftCertificates().getTags());
    }

    private OrderDto getOrderDto() {
        UserDto user = UserDto.builder()
                .name("Alex")
                .build();

        TagDto tag1 = TagDto.builder().name("tag").build();
        TagDto tag2 = TagDto.builder().name("tag 1").build();
        TagDto tag3 = TagDto.builder().name("tag 2").build();
        List<TagDto> tags = new ArrayList<>(Arrays.asList(tag1, tag2, tag3));

        GiftCertificateDto certificate = GiftCertificateDto.builder()
                .name("gift")
                .description("this is a gift")
                .price(new BigDecimal("14.00"))
                .duration(10)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(tags)
                .build();

        return OrderDto.builder()
                .cost(new BigDecimal("14.00"))
                .purchaseDate(LocalDateTime.now())
                .user(user)
                .giftCertificates(certificate)
                .build();
    }

    @Transactional
    @ParameterizedTest
    @ValueSource(longs = 4L)
    void checkDeleteByIdShouldDeleteOrderAndThrowsExceptionWhenFindThisCertificate(Long id) {
        service.deleteById(id);
        assertThrows(OrderNotFoundException.class, () -> {
            service.findById(id);
        });
    }

    @ParameterizedTest
    @ValueSource(longs = 1)
    void checkFindByUserShouldReturnUsersOrders(Long id) {
        List<OrderDto> actual = service.findByUser(id);
        assertEquals(2, actual.size());
    }

    @ParameterizedTest
    @ValueSource(longs = 1)
    void checkFindInfoShouldReturnExpectedCostAndDate(Long id) {
        OrderInfoDto expected = OrderInfoDto.builder()
                .cost(new BigDecimal("14.00"))
                .purchaseDate(LocalDateTime.of(2023, 5, 11, 8, 13))
                .build();

        OrderInfoDto actual = service.findInfo(id);

        assertEquals(expected.getCost(), actual.getCost());
        assertEquals(expected.getPurchaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                actual.getPurchaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }
}
