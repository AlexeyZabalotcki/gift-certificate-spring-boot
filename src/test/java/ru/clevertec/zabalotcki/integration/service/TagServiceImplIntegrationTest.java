package ru.clevertec.zabalotcki.integration.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.zabalotcki.builder.TestBuilder;
import ru.clevertec.zabalotcki.builder.dto.TagDtoBuilder;
import ru.clevertec.zabalotcki.dto.TagDto;
import ru.clevertec.zabalotcki.exception.EntityNotFoundException;
import ru.clevertec.zabalotcki.integration.BaseIntegrationTest;
import ru.clevertec.zabalotcki.model.Tag;
import ru.clevertec.zabalotcki.service.impl.TagServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TagServiceImplIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private TagServiceImpl service;

    private static TagDto tagDto;

    @BeforeEach
    void setUp() {
        TestBuilder<TagDto> testBuilder2 = new TagDtoBuilder();
        tagDto = testBuilder2.build();
    }

    @Test
    void checkFindAllShouldReturnAllUsers() {
        List<TagDto> actualTags = service.findAll();
        assertEquals(3, actualTags.size());
    }

    @ParameterizedTest
    @ValueSource(longs = 1L)
    void checkFindByIdShouldReturnExpectedTag(Long id) {
        TagDto expectedTag = TagDto.builder()
                .name("tag")
                .build();
        TagDto actual = service.findById(id);
        assertEquals(expectedTag, actual);
    }

    @Test
    @Transactional
    void checkSaveShouldSaveNewTag() {
        tagDto.setId(null);
        TagDto actual = service.save(tagDto);
        assertEquals(tagDto, actual);
    }

    @Transactional
    @ParameterizedTest
    @ValueSource(longs = 1L)
    void checkUpdateShouldUpdateTag(Long id) {
        tagDto.setId(null);
        TagDto actualUpdated = service.update(id, tagDto);
        assertEquals(tagDto, actualUpdated);
    }

    @Test
    @Transactional
    void checkSaveShouldReturnSavedTag() {
        tagDto.setId(null);
        TagDto actualSaved = service.save(tagDto);
        assertEquals(tagDto, actualSaved);
    }

    @Transactional
    @ParameterizedTest
    @ValueSource(longs = 1L)
    void checkDeleteByIdShouldDeleteTagAndThrowsExceptionWhenFindThisTag(Long id) {
        service.deleteById(id);
        assertThrows(EntityNotFoundException.class, () -> {
            service.findById(id);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = "tag 1")
    void checkFindIdByNameShouldReturnExpectedId(String name) {
        Long actualId = service.findIdByName(name);
        assertEquals(2, actualId);
    }

    @ParameterizedTest
    @ValueSource(strings = "tag")
    void checkFindByNameShouldReturnExpectedTag(String name) {
        Tag expected = Tag.builder().name("tag").build();
        Tag actual = service.findByName(name);
        assertEquals(expected.equals(actual), actual.equals(expected));
    }
}
