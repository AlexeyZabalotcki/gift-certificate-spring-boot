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
import ru.clevertec.zabalotcki.builder.dto.TagDtoBuilder;
import ru.clevertec.zabalotcki.dto.TagDto;
import ru.clevertec.zabalotcki.integration.BaseIntegrationTest;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TagControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private static TagDto tagDto;

    @BeforeEach
    void setUp() {
        TestBuilder<TagDto> testBuilder2 = new TagDtoBuilder();
        tagDto = testBuilder2.build();
    }

    @Test
    void checkGetAllShouldReturnStatus200() {
        webTestClient.get()
                .uri("tags/")
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
                .uri("tags/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @ParameterizedTest
    @ValueSource(longs = 7L)
    void checkFindByIdShouldReturnStatus404(Long id) {
        webTestClient.get()
                .uri("tags/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Transactional
    void checkAddShouldSaveNewEntityAndReturnStatus201() {
        tagDto.setId(4L);

        webTestClient.post()
                .uri("tags/add")
                .body(Mono.just(tagDto), TagDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated();
    }

    @Transactional
    @ParameterizedTest
    @ValueSource(longs = 1L)
    void checkUpdateShouldUpdateEntityAndReturnStatus201(Long id) {
        webTestClient.put()
                .uri("tags/update?id=" + id)
                .body(Mono.just(tagDto), TagDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Transactional
    @ParameterizedTest
    @ValueSource(longs = 3L)
    void checkDeleteByIdShouldReturnStatus200(Long id) {
        webTestClient.delete()
                .uri("tags/delete/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }
}
