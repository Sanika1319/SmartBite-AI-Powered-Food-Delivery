package com.example.repository;

import com.example.entities.OrderStatus;
import com.example.entities.Orders;
import com.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Long> {
    List<Orders> findByUser(User user);


    // All orders by status
    List<Orders> findByStatus(OrderStatus status);

    // Orders by user and status (recommended)
    List<Orders> findByUserAndStatus(User user, OrderStatus status);

    List<Orders> findByStatusAndStatusUpdatedAtBefore(
            OrderStatus status, LocalDateTime time);
}
