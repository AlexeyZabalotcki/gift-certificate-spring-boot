package ru.clevertec.zabalotcki.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.zabalotcki.builder.GiftCertificateBuilder;
import ru.clevertec.zabalotcki.builder.TestBuilder;
import ru.clevertec.zabalotcki.builder.dto.GiftCertificateDtoBuilder;
import ru.clevertec.zabalotcki.dao.GiftCertificateRepository;
import ru.clevertec.zabalotcki.dto.GiftCertificateDto;
import ru.clevertec.zabalotcki.exception.EntityNotFoundException;
import ru.clevertec.zabalotcki.mapper.GiftCertificateMapper;
import ru.clevertec.zabalotcki.mapper.TagMapper;
import ru.clevertec.zabalotcki.model.GiftCertificate;
import ru.clevertec.zabalotcki.service.TagService;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @InjectMocks
    private GiftCertificateServiceImpl service;

    @Mock
    private GiftCertificateRepository repository;

    @Mock
    private GiftCertificateMapper certificateMapper;

    @Mock
    private TagService tagService;

    @Mock
    private TagMapper tagMapper;

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
    void checkFindAllShouldReturnListCertificates() {
        Specification<GiftCertificate> specification = mock(Specification.class);
        Pageable pageable = mock(Pageable.class);
        Page<GiftCertificate> expectedPage = mock(Page.class);
        when(repository.findAll(specification, pageable)).thenReturn(expectedPage);

        Page<GiftCertificate> actualPage = service.findAll(specification, pageable);

        assertEquals(expectedPage, actualPage);
        verify(repository).findAll(specification, pageable);
    }

    static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of(1L),
                Arguments.of(2L),
                Arguments.of(3L),
                Arguments.of(4L)
        );
    }

    @ParameterizedTest
    @MethodSource("params")
    void checkFindByIdIdShouldReturnExpectedCertificate(Long id) {

        when(repository.findById(id)).thenReturn(java.util.Optional.ofNullable(certificate));
        when(certificateMapper.toDto(certificate)).thenReturn(certificateDto);

        GiftCertificateDto actual = service.findById(id);

        assertNotNull(actual);

        verify(repository, times(1)).findById(id);
    }

    @ParameterizedTest
    @MethodSource("params")
    void checkFindByIdEntityIdShouldReturnExpectedCertificate(Long id) {

        when(repository.findById(id)).thenReturn(java.util.Optional.ofNullable(certificate));

        GiftCertificate actual = service.findByIdEntity(id);

        assertNotNull(actual);

        verify(repository, times(1)).findById(id);
    }

    @ParameterizedTest
    @MethodSource("params")
    public void checkFindByIdShouldThrowEntityNotFoundException(Long id) {
        when(repository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            service.findById(id);
        });
    }

    @Test
    void checkSaveShouldSaveCertificate() {
        when(certificateMapper.toEntity(certificateDto)).thenReturn(certificate);
        when(repository.save(certificate)).thenReturn(certificate);
        when(certificateMapper.toDto(certificate)).thenReturn(certificateDto);

        GiftCertificateDto actual = service.save(certificateDto);

        assertNotNull(actual);
        verify(repository, times(1)).save(certificate);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void checkDeleteByIdShouldDeleteCertificates(Long id) {
        doNothing().when(repository).deleteById(id);

        service.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }
}
