package com.example.Schedular;

import com.example.Exception.OrderNotFoundException;
import com.example.entities.OrderStatus;
import com.example.entities.Orders;
import com.example.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UpdateOrderStatus {


    @Autowired
    private OrdersRepository ordersRepository;

    @Scheduled(fixedRate = 300000) // every 5 min
    public void updateOrderStatus() {

        LocalDateTime now = LocalDateTime.now();

        // PLACED → ON_THE_WAY after 5 min
        List<Orders> placedOrders =
                ordersRepository.findByStatusAndStatusUpdatedAtBefore(
                        OrderStatus.PLACED, now.minusMinutes(5));

        for (Orders order : placedOrders) {
            order.setStatus(OrderStatus.ON_THE_WAY);
            order.setStatusUpdatedAt(now);
        }
        ordersRepository.saveAll(placedOrders);

        // ON_THE_WAY → DELIVERED after 15 min
        List<Orders> onTheWayOrders =
                ordersRepository.findByStatusAndStatusUpdatedAtBefore(
                        OrderStatus.ON_THE_WAY, now.minusMinutes(15));

        for (Orders order : onTheWayOrders) {
            order.setStatus(OrderStatus.DELIVERED);
            order.setStatusUpdatedAt(now);
        }
        ordersRepository.saveAll(onTheWayOrders);
    }

}
