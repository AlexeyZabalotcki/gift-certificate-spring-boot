package ru.clevertec.zabalotcki.exception;

import lombok.ToString;

@ToString
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String massage) {
        super(massage);
    }
}
