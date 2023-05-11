package ru.clevertec.zabalotcki.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.zabalotcki.dto.GiftCertificateDto;
import ru.clevertec.zabalotcki.model.GiftCertificate;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GiftCertificateMapper {

    private final TagMapper tagMapper;

    public GiftCertificateDto toDto(GiftCertificate entity) {
        return GiftCertificateDto.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .duration(entity.getDuration())
                .createDate(entity.getCreateDate())
                .lastUpdateDate(entity.getLastUpdateDate())
                .tags(tagMapper.toDtoList(entity.getTags()))
                .build();
    }

    public GiftCertificate toEntity(GiftCertificateDto dto) {
        return GiftCertificate.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .duration(dto.getDuration())
                .createDate(dto.getCreateDate())
                .lastUpdateDate(dto.getLastUpdateDate())
                .tags(tagMapper.toEntityList(dto.getTags()))
                .build();
    }

    public List<GiftCertificateDto> toDtoList(List<GiftCertificate> giftCertificates) {
        if (giftCertificates == null) {
            return null;
        }
        List<GiftCertificateDto> giftCertificateDtos = new ArrayList<>();
        for (GiftCertificate giftCertificate : giftCertificates) {
            giftCertificateDtos.add(toDto(giftCertificate));
        }

        return giftCertificateDtos;
    }

    public List<GiftCertificate> toEntityList(List<GiftCertificateDto> certificate) {
        if (certificate == null) {
            return null;
        }
        List<GiftCertificate> certificates = new ArrayList<>();
        for (GiftCertificateDto certificateDto : certificate) {
            certificates.add(toEntity(certificateDto));
        }

        return certificates;
    }
}
