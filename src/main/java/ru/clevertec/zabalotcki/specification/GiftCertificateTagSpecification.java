package ru.clevertec.zabalotcki.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.zabalotcki.model.GiftCertificate;
import ru.clevertec.zabalotcki.model.Tag;

public class GiftCertificateTagSpecification {

    public static Specification<GiftCertificate> contains(String keyword) {
        return (root, query, criteriaBuilder) -> {
            Join<GiftCertificate, Tag> tagsJoin = root.join("tags", JoinType.LEFT);
            Predicate name = criteriaBuilder.like(root.get("name"), "%" + keyword + "%");
            Predicate description = criteriaBuilder.like(root.get("description"), "%" + keyword + "%");
            Predicate tags = criteriaBuilder.like(tagsJoin.get("name"), "%" + keyword + "%");
            return criteriaBuilder.or(name, description,  tags);
        };
    }
}
