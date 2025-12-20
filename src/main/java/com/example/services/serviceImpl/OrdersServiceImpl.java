package com.example.services.serviceImpl;

import com.example.Exception.*;
import com.example.entities.*;
import com.example.repository.*;
import com.example.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    @Override
    public Orders placeOrder(Long userId, Long addressId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserIdNotFoundException("User Not Found : " + userId));
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException("Address Not Found: " + addressId));
        Cart cart = cartRepository.findByUser(user);
        if (cart == null || cart.getQuantity()== 0){
            throw new CartNotFoundException("Cart is empty");
        }


//        /create order

        Orders order = new Orders();
        order.setUser(user);
        order.setTotalAmount(cart.getTotalAmount());
        order.setDeliveryAddress(address);
        order.setStatus(OrderStatus.PLACED);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatusUpdatedAt(LocalDateTime.now());
        Orders savedOrders = ordersRepository.save(order);

        //  Clear cart after order
        cart.getCartItems().clear();
        cart.setQuantity(0);
        cart.setTotalAmount(0);
        cartRepository.save(cart);

        // 🔥 DELETE requires transaction
        cartItemRepository.deleteByCart(cart);

        return savedOrders;

    }

    @Override
    public Orders getOrderById(Long orderId) {
        return ordersRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order Not Found: "+orderId));
    }

    @Override
    public List<Orders> getOrdersByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserIdNotFoundException("User Id Not found" + userId));
        return ordersRepository.findByUser(user);
    }

    @Override
    public List<Orders> getPlacedOrders() {
        return ordersRepository.findByStatus(OrderStatus.PLACED);
    }

    @Override
    public List<Orders> getOnTheWayOrders() {
        return ordersRepository.findByStatus(OrderStatus.ON_THE_WAY);
    }

    @Override
    public List<Orders> getDeliveredOrders() {
        return ordersRepository.findByStatus(OrderStatus.DELIVERED);
    }

    @Override
    public List<Orders> getCancelledOrders() {
        return ordersRepository.findByStatus(OrderStatus.CANCELLED);
    }


    @Override
    public void cancelOrder(Long orderId) {

        Orders orders = ordersRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order Not Found: " + orderId));

        if (orders.getStatus() == OrderStatus.CANCELLED ||
                orders.getStatus() == OrderStatus.DELIVERED) {
            throw new OrderCannotCancelException("Delivered or cancelled order cannot be cancelled");
        }

        orders.setStatus(OrderStatus.CANCELLED);
        orders.setStatusUpdatedAt(LocalDateTime.now());
        ordersRepository.save(orders);
    }

}
