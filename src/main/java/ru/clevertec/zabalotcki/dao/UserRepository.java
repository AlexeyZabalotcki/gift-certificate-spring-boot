package ru.clevertec.zabalotcki.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.clevertec.zabalotcki.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT t.name FROM Tag t " +
            "JOIN t.giftCertificates gc " +
            "JOIN gc.orders o " +
            "JOIN o.user u " +
            "WHERE u.id = :userId " +
            "GROUP BY t.name ORDER BY COUNT(t.id) DESC, SUM(o.cost) DESC")
    List<String> findTag(@Param("userId") Long userId);
}
