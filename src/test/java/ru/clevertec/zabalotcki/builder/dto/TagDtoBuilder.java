package ru.clevertec.zabalotcki.builder.dto;

import ru.clevertec.zabalotcki.builder.TestBuilder;
import ru.clevertec.zabalotcki.dto.TagDto;

public class TagDtoBuilder implements TestBuilder<TagDto> {

    private Long id = 1L;
    private String name = "Tag";

    @Override
    public TagDto build() {
        return TagDto.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
