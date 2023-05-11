package ru.clevertec.zabalotcki.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.zabalotcki.dto.GiftCertificateDto;
import ru.clevertec.zabalotcki.exception.EntityNotFoundException;
import ru.clevertec.zabalotcki.mapper.GiftCertificateMapper;
import ru.clevertec.zabalotcki.model.GiftCertificate;
import ru.clevertec.zabalotcki.service.GiftCertificateService;
import ru.clevertec.zabalotcki.specification.GiftCertificateTagSpecification;

import java.util.Optional;

@RestController
@RequestMapping("/gifts")
@RequiredArgsConstructor
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateMapper giftCertificateMapper;

    private static final int INITIAL_PAGE = 0;
    private static final int PAGE_SIZE = 10;

    @GetMapping("/all")
    public ResponseEntity<Page<GiftCertificateDto>> getAll(@RequestParam("page") Optional<Integer> page,
                                                           @RequestParam(value = "keyword", required = false) String keyword,
                                                           @RequestParam(value = "sort", required = false) String sort) {

        final int currentPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Specification<GiftCertificate> giftCertificateSpec = Specification.where(null);

        if (keyword != null) {
            giftCertificateSpec = giftCertificateSpec.and(GiftCertificateTagSpecification.contains(keyword));
        }
        if (sort != null) {
            Sort sortBy = Sort.by(sort).ascending();
            Page<GiftCertificate> certificates = giftCertificateService
                    .findAll(giftCertificateSpec, PageRequest.of(currentPage, PAGE_SIZE, sortBy));
            return ResponseEntity.ok(certificates.map(giftCertificateMapper::toDto));
        } else {
            Page<GiftCertificate> certificates = giftCertificateService
                    .findAll(giftCertificateSpec, PageRequest.of(currentPage, PAGE_SIZE));
            return ResponseEntity.ok(certificates.map(giftCertificateMapper::toDto));
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> findById(@PathVariable("id") Long id) {
        GiftCertificateDto giftCertificateDto;
        try {
            giftCertificateDto = giftCertificateService.findById(id);
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            return new ResponseEntity("Check gift certificate id", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(giftCertificateDto);

    }

    @PostMapping
    public ResponseEntity<GiftCertificateDto> add(@RequestBody GiftCertificateDto dto) {
        GiftCertificateDto saved = giftCertificateService.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<GiftCertificateDto> update(@RequestParam Long id,
                                                     @RequestBody GiftCertificateDto dto) {
        giftCertificateService.update(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GiftCertificateDto> deleteById(@PathVariable Long id) {
        try {
            giftCertificateService.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
            return new ResponseEntity("That id: " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
