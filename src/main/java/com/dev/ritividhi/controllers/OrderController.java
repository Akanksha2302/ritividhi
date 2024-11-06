package com.dev.ritividhi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


import com.dev.ritividhi.models.Order;
import com.dev.ritividhi.services.OrderService;
import com.dev.ritividhi.utils.CommonUtils;

@RestController
@RequestMapping("/ritividhi/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId) {
        Optional<Order> order = orderService.getOrderById(orderId);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/createOrder")
    public Order createOrder(@RequestBody Order order, @RequestHeader("Authorization") String bearerToken) {
    	String token = CommonUtils.extractBearerToken(bearerToken);
        return orderService.createOrder(order,token);   
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId,@RequestHeader("Authorization") String bearerToken) {
    	String token = CommonUtils.extractBearerToken(bearerToken);
        orderService.deleteOrder(orderId,token);
        return ResponseEntity.noContent().build();
    }
}