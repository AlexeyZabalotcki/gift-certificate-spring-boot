package ru.clevertec.zabalotcki.integration.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.zabalotcki.annotation.ClearDatabase;
import ru.clevertec.zabalotcki.builder.TestBuilder;
import ru.clevertec.zabalotcki.builder.dto.GiftCertificateDtoBuilder;
import ru.clevertec.zabalotcki.dto.GiftCertificateDto;
import ru.clevertec.zabalotcki.dto.TagDto;
import ru.clevertec.zabalotcki.exception.EntityNotFoundException;
import ru.clevertec.zabalotcki.integration.BaseIntegrationTest;
import ru.clevertec.zabalotcki.model.GiftCertificate;
import ru.clevertec.zabalotcki.service.impl.GiftCertificateServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ClearDatabase
class GiftCertificateServiceImplIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private GiftCertificateServiceImpl service;

    private static GiftCertificateDto certificateDto;

    @BeforeEach
    void setUp() {
        TestBuilder<GiftCertificateDto> testBuilder2 = new GiftCertificateDtoBuilder();
        certificateDto = testBuilder2.build();
    }

    @Test
    void checkFindAllShouldReturnNotNullResult() {
        Page<GiftCertificate> actual = service.findAll(null, PageRequest.of(0, 10));
        assertNotNull(actual);
    }

    @ParameterizedTest
    @ValueSource(longs = 1L)
    void checkFindByIdShouldReturnExpectedCertificate(Long id) {
        GiftCertificateDto expected = getGiftCertificateDto();
        GiftCertificateDto actual = service.findById(id);

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getDuration(), actual.getDuration());
        assertEquals(expected.getPrice().floatValue(), actual.getPrice().floatValue());
        assertEquals(expected.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                actual.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        assertEquals(expected.getLastUpdateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                actual.getLastUpdateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        assertEquals(expected.getTags(), actual.getTags());

    }

    private GiftCertificateDto getGiftCertificateDto() {
        TagDto tag1 = TagDto.builder().name("tag").build();
        TagDto tag2 = TagDto.builder().name("tag 1").build();
        TagDto tag3 = TagDto.builder().name("tag 2").build();
        List<TagDto> tags = new ArrayList<>(Arrays.asList(tag1, tag2, tag3));
        return GiftCertificateDto.builder()
                .name("gift")
                .description("this is a gift")
                .price(new BigDecimal("14.00"))
                .duration(10)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .tags(tags)
                .build();
    }

    @Test
    @Transactional
    void checkSaveShouldSaveNewCertificate() {
        certificateDto.setId(null);
        certificateDto.getTags().forEach(t -> t.setId(null));

        service.save(certificateDto);

        GiftCertificateDto actual = service.findById(6L);

        assertEquals(certificateDto.getName(), actual.getName());
        assertEquals(certificateDto.getDescription(), actual.getDescription());
        assertEquals(certificateDto.getDuration(), actual.getDuration());
        assertEquals(certificateDto.getPrice().floatValue(), actual.getPrice().floatValue());
        assertEquals(certificateDto.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                actual.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        assertEquals(certificateDto.getLastUpdateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                actual.getLastUpdateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        assertEquals(certificateDto.getTags(), actual.getTags());
    }

    @Test
    @Transactional
    void checkSaveShouldReturnSavedCertificate() {
        certificateDto.setId(null);
        certificateDto.getTags().forEach(t -> t.setId(null));

        GiftCertificateDto actual = service.save(certificateDto);

        assertEquals(certificateDto.getName(), actual.getName());
        assertEquals(certificateDto.getDescription(), actual.getDescription());
        assertEquals(certificateDto.getDuration(), actual.getDuration());
        assertEquals(certificateDto.getPrice().floatValue(), actual.getPrice().floatValue());
        assertEquals(certificateDto.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                actual.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        assertEquals(certificateDto.getLastUpdateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                actual.getLastUpdateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        assertEquals(certificateDto.getTags(), actual.getTags());
    }

    @Transactional
    @ParameterizedTest
    @ValueSource(longs = 5L)
    void checkDeleteByIdShouldDeleteCertificateAndThrowsExceptionWhenFindThisCertificate(Long id) {
        service.deleteById(id);
        assertThrows(EntityNotFoundException.class, () -> {
            service.findById(id);
        });
    }
}
