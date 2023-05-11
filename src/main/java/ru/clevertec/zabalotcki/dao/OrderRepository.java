package ru.clevertec.zabalotcki.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.clevertec.zabalotcki.model.Order;
import ru.clevertec.zabalotcki.model.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.user = :user")
    List<Order> findByUser(@Param("user") User user);
}
