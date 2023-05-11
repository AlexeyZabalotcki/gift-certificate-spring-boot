package ru.clevertec.zabalotcki.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.clevertec.zabalotcki.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Modifying
    @Query("update Tag t set t.name =:name where t.id = :id")
    void update(@Param("id") Long id,
                @Param("name") String name);

    Tag findByName(String name);

    @Query("SELECT t.id from Tag t WHERE  t.name = :name")
    Long findIdByName(String name);
}
