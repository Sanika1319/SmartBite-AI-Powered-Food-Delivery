package com.example.services;

import com.example.entities.Orders;

import java.util.List;

public interface OrdersService {
    // CREATE
    Orders placeOrder(Long userId, Long addressId);

    // READ
    Orders getOrderById(Long orderId);
    List<Orders> getOrdersByUser(Long userId);
    List<Orders> getPlacedOrders();

    List<Orders> getOnTheWayOrders();

    List<Orders> getDeliveredOrders();
    List<Orders> getCancelledOrders();


    // CANCEL
    void cancelOrder(Long orderId);
}
