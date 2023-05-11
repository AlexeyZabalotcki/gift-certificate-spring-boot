package ru.clevertec.zabalotcki.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.zabalotcki.dto.GiftCertificateDto;
import ru.clevertec.zabalotcki.model.GiftCertificate;

public interface GiftCertificateService {

    Page<GiftCertificate> findAll(Specification<GiftCertificate> specification, Pageable pageable);

    GiftCertificateDto findById(Long id);

    GiftCertificateDto save(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto update(Long id, GiftCertificateDto certificate);

    void deleteById(Long id);

    GiftCertificate findByIdEntity(Long id);
}
