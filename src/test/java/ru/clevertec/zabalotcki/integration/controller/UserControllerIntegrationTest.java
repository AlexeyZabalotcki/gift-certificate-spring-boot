package ru.clevertec.zabalotcki.integration.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.clevertec.zabalotcki.builder.TestBuilder;
import ru.clevertec.zabalotcki.builder.dto.UserDtoBuilder;
import ru.clevertec.zabalotcki.dto.UserDto;
import ru.clevertec.zabalotcki.integration.BaseIntegrationTest;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private static UserDto userDto;

    @BeforeEach
    void setUp() {
        TestBuilder<UserDto> testBuilder2 = new UserDtoBuilder();
        userDto = testBuilder2.build();
    }

    @Test
    void checkFindAllShouldReturnStatus200() {
        webTestClient.get()
                .uri("user/")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$").isNotEmpty();
    }


    @ParameterizedTest
    @ValueSource(longs = 1L)
    void checkFindByIdShouldReturnExpectedGiftCertificate(Long id) {
        webTestClient.get()
                .uri("user/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Andy");
    }

    @ParameterizedTest
    @ValueSource(longs = 7L)
    void checkFindByIdShouldReturnStatus404(Long id) {
        webTestClient.get()
                .uri("user/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Transactional
    void checkAddShouldSaveNewEntityAndReturnStatus201() {
        userDto.setId(4L);

        webTestClient.post()
                .uri("user/")
                .body(Mono.just(userDto), UserDto.class)
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
                .jsonPath("$").isEqualTo("tag 1");
    }

    @Transactional
    @ParameterizedTest
    @ValueSource(longs = 3L)
    void checkDeleteByIdShouldReturnStatus200(Long id) {
        webTestClient.delete()
                .uri("user/delete/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }
}
