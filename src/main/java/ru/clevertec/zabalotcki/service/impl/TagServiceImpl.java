package ru.clevertec.zabalotcki.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.zabalotcki.dao.TagRepository;
import ru.clevertec.zabalotcki.dto.TagDto;
import ru.clevertec.zabalotcki.exception.EntityNotFoundException;
import ru.clevertec.zabalotcki.mapper.TagMapper;
import ru.clevertec.zabalotcki.model.Tag;
import ru.clevertec.zabalotcki.service.TagService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagDto save(TagDto tagDto) {
        Tag tag = tagMapper.toEntity(tagDto);
        Tag save = tagRepository.save(tag);
        return tagMapper.toDto(save);
    }

    @Override
    @Transactional
    public TagDto update(Long id, TagDto tagDto) {
        tagRepository.update(id, tagDto.getName());
        return tagDto;
    }

    @Override
    public void deleteById(long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public TagDto findById(Long id) {
        Optional<Tag> tag = Optional.of(tagRepository.findById(id))
                .orElseThrow(() -> new EntityNotFoundException("Check teg id " + id));
        return tagMapper.toDto(tag.get());
    }

    @Override
    public List<TagDto> findAll() {
        List<Tag> all = tagRepository.findAll();
        return tagMapper.toDtoList(all);
    }

    @Override
    public Long findIdByName(String name) {
        return tagRepository.findIdByName(name);
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }
}
