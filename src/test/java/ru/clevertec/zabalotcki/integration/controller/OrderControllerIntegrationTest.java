package ru.clevertec.zabalotcki.integration.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.clevertec.zabalotcki.builder.TestBuilder;
import ru.clevertec.zabalotcki.builder.dto.OrderDtoBuilder;
import ru.clevertec.zabalotcki.dto.OrderDto;
import ru.clevertec.zabalotcki.integration.BaseIntegrationTest;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private static OrderDto orderDto;

    @BeforeEach
    void setUp() {
        TestBuilder<OrderDto> testBuilder2 = new OrderDtoBuilder();
        orderDto = testBuilder2.build();
    }

    @Test
    void checkFindAllShouldReturnStatus200() {
        webTestClient.get()
                .uri("order/")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$").isNotEmpty();
    }


    @ParameterizedTest
    @ValueSource(longs = 1L)
    void checkFindByIdShouldReturnStatus200(Long id) {
        webTestClient.get()
                .uri("order/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @ParameterizedTest
    @ValueSource(longs = 7L)
    void checkFindByIdShouldReturnStatus404(Long id) {
        webTestClient.get()
                .uri("order/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @ParameterizedTest
    @ValueSource(longs = 1L)
    void checkFindByUserShouldReturnStatus200(Long id) {
        webTestClient.get()
                .uri("order/find?userId=" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @ParameterizedTest
    @ValueSource(longs = 1L)
    void checkFindOrderByParamsShouldReturnStatus200(Long id) {
        webTestClient.get()
                .uri("order/find/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.cost").isEqualTo("14.0")
                .jsonPath("$.purchaseDate").isEqualTo("2023-05-11T08:13:14.767928");
    }

    @Transactional
    @ParameterizedTest
    @CsvSource({"1,1"})
    void checkAddShouldSaveNewEntityAndReturnStatus201(Long userId, Long certId) {
        orderDto.setId(5L);
        webTestClient.post()
                .uri("order/add?userId=" + userId + "&certId=" + certId)
                .body(Mono.just(orderDto), OrderDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated();
    }

    @ParameterizedTest
    @ValueSource(longs = 1L)
    void checkFindTagShouldReturnStatus200(Long id) {
        webTestClient.get()
                .uri("user/tag?userId=" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isEqualTo("tag 2");
    }
}
