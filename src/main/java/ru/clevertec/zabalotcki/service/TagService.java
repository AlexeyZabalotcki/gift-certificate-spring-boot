package ru.clevertec.zabalotcki.service;

import ru.clevertec.zabalotcki.dto.TagDto;
import ru.clevertec.zabalotcki.model.Tag;

import java.util.List;

public interface TagService {

    TagDto save(TagDto tagDto);

    TagDto update(Long id,TagDto tagDto);

    void deleteById(long id);

    TagDto findById(Long id);


    List<TagDto> findAll();

    Long findIdByName(String name);

    Tag findByName(String name);
}
