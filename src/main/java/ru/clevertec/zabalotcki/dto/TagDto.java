package ru.clevertec.zabalotcki.dto;

import lombok.*;

@Data
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    private Long id;
    private String name;
}
