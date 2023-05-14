package ru.clevertec.zabalotcki.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.zabalotcki.builder.TagBuilder;
import ru.clevertec.zabalotcki.builder.TestBuilder;
import ru.clevertec.zabalotcki.builder.dto.TagDtoBuilder;
import ru.clevertec.zabalotcki.dto.TagDto;
import ru.clevertec.zabalotcki.model.Tag;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class TagMapperTest {

    @InjectMocks
    private TagMapper tagMapper;

    private static Tag tag;
    private static TagDto tagDto;

    @BeforeEach
    void setUp() {
        TestBuilder<Tag> testBuilder1 = new TagBuilder();
        TestBuilder<TagDto> testBuilder2 = new TagDtoBuilder();
        tag = testBuilder1.build();
        tagDto = testBuilder2.build();
    }

    @Test
    void checkToEntityShouldConvertDtoToEntity() {
        tag.setId(null);
        Tag actualTag = tagMapper.toEntity(tagDto);
        assertEquals(tag, actualTag);
    }

    @Test
    void checkToDtoShouldConvertEntityToDto() {
        tagDto.setId(null);
        TagDto actualTag = tagMapper.toDto(tag);
        assertEquals(tagDto, actualTag);
    }

    @Test
    void checkToDtoListShouldReturnEmptyListWhenGivenNull() {
        List<TagDto> result = tagMapper.toDtoList(null);

        assertNull(result);
    }

    @Test
    void checkToEntityListShouldReturnConvertedList() {
        List<TagDto> tags = Collections.singletonList(tagDto);

        List<Tag> result = tagMapper.toEntityList(tags);

        assertEquals(tags.size(), result.size());
    }

    @Test
    void checkToDtoListShouldReturnConvertedList() {
        List<Tag> tags = Collections.singletonList(tag);

        List<TagDto> result = tagMapper.toDtoList(tags);

        assertEquals(tags.size(), result.size());
    }
}
