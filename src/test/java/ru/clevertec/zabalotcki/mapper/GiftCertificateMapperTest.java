package ru.clevertec.zabalotcki.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.zabalotcki.builder.GiftCertificateBuilder;
import ru.clevertec.zabalotcki.builder.TestBuilder;
import ru.clevertec.zabalotcki.builder.dto.GiftCertificateDtoBuilder;
import ru.clevertec.zabalotcki.dto.GiftCertificateDto;
import ru.clevertec.zabalotcki.model.GiftCertificate;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateMapperTest {

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private GiftCertificateMapper certificateMapper;

    private static GiftCertificate certificate;
    private static GiftCertificateDto certificateDto;

    @BeforeEach
    void setUp() {
        TestBuilder<GiftCertificate> testBuilder1 = new GiftCertificateBuilder();
        TestBuilder<GiftCertificateDto> testBuilder2 = new GiftCertificateDtoBuilder();
        certificate = testBuilder1.build();
        certificateDto = testBuilder2.build();

    }

    @Test
    void checkToDtoShouldConvertEntityToDto() {
        certificateDto.setId(null);

        when(tagMapper.toDtoList(certificate.getTags())).thenReturn(certificateDto.getTags());

        GiftCertificateDto actual = certificateMapper.toDto(certificate);

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
    void checkToEntityShouldConvertDtoToEntity() {
        certificate.setId(null);

        when(tagMapper.toEntityList(certificateDto.getTags())).thenReturn(certificate.getTags());

        GiftCertificate actual = certificateMapper.toEntity(certificateDto);

        assertEquals(certificate.getName(), actual.getName());
        assertEquals(certificate.getDescription(), actual.getDescription());
        assertEquals(certificate.getDuration(), actual.getDuration());
        assertEquals(certificate.getPrice().floatValue(), actual.getPrice().floatValue());
        assertEquals(certificate.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                actual.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        assertEquals(certificate.getLastUpdateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                actual.getLastUpdateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        assertEquals(certificate.getTags(), actual.getTags());
    }
}
