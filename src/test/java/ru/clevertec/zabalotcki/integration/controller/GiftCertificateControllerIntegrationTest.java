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
import ru.clevertec.zabalotcki.builder.dto.GiftCertificateDtoBuilder;
import ru.clevertec.zabalotcki.dto.GiftCertificateDto;
import ru.clevertec.zabalotcki.integration.BaseIntegrationTest;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GiftCertificateControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private static GiftCertificateDto certificateDto;

    @BeforeEach
    void setUp() {
        TestBuilder<GiftCertificateDto> testBuilder2 = new GiftCertificateDtoBuilder();
        certificateDto = testBuilder2.build();
    }

    @Test
    void checkGetAllShouldReturnStatus200() {
        webTestClient.get()
                .uri("gifts/all?sort=name&keyword=")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content").isArray()
                .jsonPath("$.content").isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(longs = 1L)
    void checkFindByIdShouldReturnExpectedGiftCertificate(Long id) {
        webTestClient.get()
                .uri("gifts/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("gift")
                .jsonPath("$.description").isEqualTo("this is a gift");
    }

    @ParameterizedTest
    @ValueSource(longs = 7L)
    void checkFindByIdShouldReturnStatus404(Long id) {
        webTestClient.get()
                .uri("gifts/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Transactional
    void checkAddShouldSaveNewEntityAndReturnStatus201() {
        certificateDto.setId(6L);

        webTestClient.post()
                .uri("/gifts")
                .body(Mono.just(certificateDto), GiftCertificateDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated();
    }

    @Transactional
    @ParameterizedTest
    @ValueSource(longs = 5L)
    void checkDeleteByIdShouldReturnStatus200(Long id) {
        webTestClient.delete()
                .uri("gifts/delete/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }
}
