package com.example.controllers;

import com.example.entities.Orders;
import com.example.services.OrdersService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrdersService ordersService;

    @PostMapping("placeOrder/{userId}/{addressId}")
    public ResponseEntity<Orders> placeOrder(@PathVariable Long userId,@PathVariable Long addressId){
        return new ResponseEntity<>(ordersService.placeOrder(userId,addressId), HttpStatus.CREATED);
    }

    @GetMapping("/getOrderById/{orderId}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long orderId){
        return new ResponseEntity<>(ordersService.getOrderById(orderId),HttpStatus.OK);
    }
    @GetMapping("/getOrdersByUser/{userId}")
    public ResponseEntity<List<Orders>> getOrdersByUser(@PathVariable Long userId){
        return new ResponseEntity<>(ordersService.getOrdersByUser(userId),HttpStatus.OK);
    }

    @GetMapping("/getPlacedOrders")
    public ResponseEntity<List<Orders>> getPlacedOrders(){
        return new ResponseEntity<>(ordersService.getPlacedOrders(),HttpStatus.OK);
    }
    @GetMapping("/getOnTheWayOrders")
    public ResponseEntity<List<Orders>> getOnTheWayOrders(){
        return new ResponseEntity<>(ordersService.getOnTheWayOrders(),HttpStatus.OK);
    }

    @GetMapping("/getDeliveredOrders")
    public ResponseEntity<List<Orders>> getDeliveredOrders(){
        return new ResponseEntity<>(ordersService.getDeliveredOrders(),HttpStatus.OK);
    }
    @GetMapping("/getCancelledOrders")
    public ResponseEntity<List<Orders>> getCancelledOrders(){
        return new ResponseEntity<>(ordersService.getCancelledOrders(),HttpStatus.OK);
    }


    @DeleteMapping("/cancelOrder/{orderId}")
    public ResponseEntity<?>  cancelOrder(@PathVariable Long orderId){
        ordersService.cancelOrder(orderId);
        return ResponseEntity.ok("Order Canceled Successfully");
    }

}
