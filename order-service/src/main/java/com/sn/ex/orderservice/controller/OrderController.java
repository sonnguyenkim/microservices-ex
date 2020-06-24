package com.sn.ex.orderservice.controller;

import com.sn.ex.orderservice.model.Order;
import com.sn.ex.orderservice.model.OrderResponse;
import com.sn.ex.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getOrderList() {
        return orderService.getOrderList();
    }

    @GetMapping("/{id}")
    public List<OrderResponse> findOrder(@PathVariable String id) {
        return orderService.getOrderResponse(id);
    }


}
