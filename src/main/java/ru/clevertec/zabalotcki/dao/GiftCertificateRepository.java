package ru.clevertec.zabalotcki.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.clevertec.zabalotcki.model.GiftCertificate;
import ru.clevertec.zabalotcki.model.Tag;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GiftCertificateRepository extends PagingAndSortingRepository<GiftCertificate, Long>,
        JpaRepository<GiftCertificate, Long>, JpaSpecificationExecutor<GiftCertificate> {

    @Modifying
    @Query("UPDATE GiftCertificate gc set gc.name = :name, gc.description = :description, gc.duration = :duration, gc.price = :price, gc.createDate = :createDate, gc.lastUpdateDate = :lastUpdateDate,gc.tags = (SELECT t FROM Tag t WHERE t.name IN (:tags)) where gc.id = :id")
    void update(@Param("id") Long id,
                @Param("name") String name,
                @Param("description") String description,
                @Param("duration") Integer duration,
                @Param("price") BigDecimal price,
                @Param("createDate") LocalDateTime createDate,
                @Param("lastUpdateDate") LocalDateTime lastUpdateDate,
                @Param("tags") List<Tag> tags);

    @Modifying
    @Query(value = "DELETE FROM gift_certificate_tag WHERE gift_certificate_id = :certificateId", nativeQuery = true)
    void deleteGiftCertificateTag(@Param("certificateId") Long certificateId);

    @Modifying
    @Query(value = "INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (:certificateId, :tagId)", nativeQuery = true)
    void insertGiftCertificateTag(@Param("certificateId") Long certificateId, @Param("tagId") Long tagId);

    @Modifying
    @Query(value = "DELETE FROM gift_certificate gc WHERE gc.id = :certificateId", nativeQuery = true)
    void deleteById(@Param("certificateId") Long certificateId);
}
