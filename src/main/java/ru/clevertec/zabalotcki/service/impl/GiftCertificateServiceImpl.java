package ru.clevertec.zabalotcki.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.zabalotcki.dao.GiftCertificateRepository;
import ru.clevertec.zabalotcki.dto.GiftCertificateDto;
import ru.clevertec.zabalotcki.exception.EntityNotFoundException;
import ru.clevertec.zabalotcki.mapper.GiftCertificateMapper;
import ru.clevertec.zabalotcki.mapper.TagMapper;
import ru.clevertec.zabalotcki.model.GiftCertificate;
import ru.clevertec.zabalotcki.model.Tag;
import ru.clevertec.zabalotcki.service.GiftCertificateService;
import ru.clevertec.zabalotcki.service.TagService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final TagService tagService;

    private final GiftCertificateMapper giftCertificateMapper;
    private final TagMapper tagMapper;

    @Override
    public Page<GiftCertificate> findAll(Specification<GiftCertificate> specification, Pageable pageable) {
        return giftCertificateRepository.findAll(specification, pageable);
    }

    @Override
    public GiftCertificateDto save(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateMapper.toEntity(giftCertificateDto);
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        checkTags(giftCertificate);
        GiftCertificate save = giftCertificateRepository.save(giftCertificate);
        return giftCertificateMapper.toDto(save);
    }

    @Override
    public GiftCertificateDto update(Long id, GiftCertificateDto certificate) {
        GiftCertificate oldCert = giftCertificateMapper.toEntity(findById(id));
        GiftCertificate newFields = giftCertificateMapper.toEntity(certificate);
        giftCertificateRepository.deleteGiftCertificateTag(id);
        GiftCertificate newCert = updateFields(id, newFields, oldCert);
        giftCertificateRepository.update(id, newCert.getName(), newCert.getDescription(),
                newCert.getDuration(), newCert.getPrice(), newCert.getCreateDate(), newCert.getLastUpdateDate(), newCert.getTags());
        return giftCertificateMapper.toDto(newCert);
    }

    private GiftCertificate updateFields(Long id, GiftCertificate newFields, GiftCertificate oldCert) {
        if (newFields.getName() != null) {
            oldCert.setName(newFields.getName());
        }
        if (newFields.getDescription() != null) {
            oldCert.setDescription(newFields.getDescription());
        }
        if (newFields.getDuration() != null) {
            oldCert.setDuration(newFields.getDuration());
        }
        if (newFields.getPrice() != null) {
            oldCert.setPrice(newFields.getPrice());
        }
        if (newFields.getTags() != null) {
            rewriteTags(id, newFields, oldCert);
        }
        oldCert.setLastUpdateDate(LocalDateTime.now());
        return oldCert;
    }

    private void rewriteTags(Long id, GiftCertificate newFields, GiftCertificate oldCert) {
        List<Tag> oldTags = oldCert.getTags();
        List<Tag> newTags = newFields.getTags();

        for (Tag newTag : newTags) {
            Tag existingTag = findTag(newTag);
            if (existingTag != null) {
                oldTags.add(existingTag);
            } else {
                tagService.save(tagMapper.toDto(newTag));
                oldTags.add(newTag);
            }

            Long idByName = tagService.findIdByName(newTag.getName());
            giftCertificateRepository.insertGiftCertificateTag(id, idByName);
        }
    }

    private Tag findTag(Tag tag) {
        return tagService.findByName(tag.getName());
    }

    private void checkTags(GiftCertificate giftCertificate) {
        List<Tag> tags = giftCertificate.getTags();
        for (int i = 0; i < tags.size(); i++) {
            Tag tag = tags.get(i);
            Tag existingTag = findTag(tag);
            if (existingTag != null) {
                tags.set(i, existingTag);
            }
        }
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        return Optional.ofNullable(giftCertificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Check the id " + id)))
                .map(giftCertificateMapper::toDto)
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        giftCertificateRepository.deleteById(id);
    }

    public GiftCertificate findByIdEntity(Long id) {
        return giftCertificateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("check id"));
    }
}
