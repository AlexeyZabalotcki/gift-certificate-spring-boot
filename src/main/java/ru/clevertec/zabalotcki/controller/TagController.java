package ru.clevertec.zabalotcki.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.zabalotcki.dto.TagDto;
import ru.clevertec.zabalotcki.exception.EntityNotFoundException;
import ru.clevertec.zabalotcki.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/")
    public ResponseEntity<List<TagDto>> getAll() {
        List<TagDto> allTags = tagService.findAll();
        return new ResponseEntity<>(allTags, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> findById(@PathVariable("id") Long id) {
        TagDto tagDto;

        try {
            tagDto = tagService.findById(id);
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            return new ResponseEntity("Check tag id", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tagDto);
    }

    @PostMapping("/add")
    public ResponseEntity<TagDto> add(@RequestBody TagDto tagDto) {
        TagDto saved = tagService.save(tagDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<TagDto> update(@RequestParam Long id,
                                         @RequestBody TagDto tagDto) {
        tagService.update(id, tagDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TagDto> deleteById(@PathVariable Long id) {
        try {
            tagService.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
            return new ResponseEntity("That id: " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
