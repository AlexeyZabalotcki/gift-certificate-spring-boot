package ru.clevertec.zabalotcki.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.zabalotcki.builder.TagBuilder;
import ru.clevertec.zabalotcki.builder.TestBuilder;
import ru.clevertec.zabalotcki.builder.dto.TagDtoBuilder;
import ru.clevertec.zabalotcki.dao.TagRepository;
import ru.clevertec.zabalotcki.dto.TagDto;
import ru.clevertec.zabalotcki.exception.EntityNotFoundException;
import ru.clevertec.zabalotcki.mapper.TagMapper;
import ru.clevertec.zabalotcki.model.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl service;

    @Mock
    private TagRepository repository;
    @Mock
    private TagMapper mapper;

    private static Tag tag;
    private static TagDto tagDto;

    @BeforeEach
    void setUp() {
        TestBuilder<Tag> testBuilder1 = new TagBuilder();
        TestBuilder<TagDto> testBuilder2 = new TagDtoBuilder();
        tag = testBuilder1.build();
        tagDto = testBuilder2.build();
    }

    static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of(1L),
                Arguments.of(2L),
                Arguments.of(3L),
                Arguments.of(4L)
        );
    }

    static Stream<Arguments> paramsName() {
        return Stream.of(
                Arguments.of("Tag")

        );
    }

    @Test
    void checkFindAllShouldReturnListTags() {
        List<Tag> expectedList = new ArrayList<>(Collections.singletonList(tag));
        List<TagDto> expectedListDto = new ArrayList<>(Collections.singletonList(tagDto));

        when(repository.findAll()).thenReturn(expectedList);
        when(mapper.toDtoList(expectedList)).thenReturn(expectedListDto);

        List<TagDto> actualList = service.findAll();

        assertNotNull(actualList);

        verify(repository, times(1)).findAll();
    }

    @ParameterizedTest
    @MethodSource("params")
    void checkFindByIdIdShouldReturnExpectedCertificate(Long id) {
        when(repository.findById(id)).thenReturn(Optional.of(tag));
        when(mapper.toDto(tag)).thenReturn(tagDto);

        TagDto actual = service.findById(id);

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
        when(mapper.toEntity(tagDto)).thenReturn(tag);
        when(repository.save(tag)).thenReturn(tag);
        when(mapper.toDto(tag)).thenReturn(tagDto);

        TagDto actual = service.save(tagDto);

        assertNotNull(actual);
        verify(repository, times(1)).save(tag);
    }

    @ParameterizedTest
    @MethodSource("params")
    void update(Long id) {
        doNothing().when(repository).update(id, tag.getName());
        TagDto actual = service.update(id, tagDto);

        assertNotNull(actual);

        verify(repository, times(1)).update(id, tag.getName());
    }

    @ParameterizedTest
    @MethodSource("params")
    void deleteById(Long id) {
        doNothing().when(repository).deleteById(id);

        service.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }

    @ParameterizedTest
    @MethodSource("paramsName")
    void checkFindIdByNameShouldReturnExpectedTag(String name) {
        when(repository.findIdByName(name)).thenReturn(tag.getId());

        Long actual = service.findIdByName(name);

        assertEquals(tag.getId(), actual);

        verify(repository, times(1)).findIdByName(name);
    }

    @ParameterizedTest
    @MethodSource("paramsName")
    void checkFindByNameShouldReturnExpectedTag(String name) {
        when(repository.findByName(name)).thenReturn(tag);

        Tag actual = service.findByName(name);

        assertEquals(tag, actual);

        verify(repository, times(1)).findByName(name);
    }
}
