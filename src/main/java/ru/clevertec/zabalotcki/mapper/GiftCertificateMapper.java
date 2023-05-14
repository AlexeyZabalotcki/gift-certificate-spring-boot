package ru.clevertec.zabalotcki.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.zabalotcki.dto.GiftCertificateDto;
import ru.clevertec.zabalotcki.model.GiftCertificate;

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
}
