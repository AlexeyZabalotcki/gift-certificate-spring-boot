package ru.clevertec.zabalotcki.mapper;

import org.springframework.stereotype.Component;
import ru.clevertec.zabalotcki.dto.TagDto;
import ru.clevertec.zabalotcki.model.Tag;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagMapper {

    public Tag toEntity(TagDto dto) {
        return Tag.builder()
                .name(dto.getName())
                .build();
    }

    public TagDto toDto(Tag entity) {
        return TagDto.builder()
                .name(entity.getName())
                .build();
    }

    public List<Tag> toEntityList(List<TagDto> tags) {
        if (tags == null) {
            return null;
        }
        List<Tag> list = new ArrayList<>();
        for (TagDto dto : tags) {
            list.add(toEntity(dto));
        }
        return list;
    }

    public List<TagDto> toDtoList(List<Tag> tags) {
        if (tags == null) {
            return null;
        }
        List<TagDto> dtos = new ArrayList<>();
        for (Tag tag : tags) {
            dtos.add(toDto(tag));
        }
        return dtos;
    }
}
