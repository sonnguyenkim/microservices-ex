package com.sn.ex.orderservice.service;

import com.sn.ex.orderservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class OrderService {

    /*
        @Autowired
        WebClient.Builder webClientBuilder;

        Alternative WebClient way
        Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/"+ rating.getMovieId())
            .retrieve().bodyToMono(Movie.class).block();
    */

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ProductService productService;

    private List<Order> orderList = new ArrayList<>();

    OrderService() {
        orderList.add(new Order(1,"Order 1"));
        orderList.add(new Order(2,"Order 2"));
        orderList.add(new Order(3,"Order 3"));
        orderList.add(new Order(4,"Order 4"));
        orderList.add(new Order(5,"Order 5"));
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public Order findOrder(String id) {
        return orderList.stream()
                .filter(order -> {
                    return order.getOrderId().equals(Integer.parseInt(id));
                }).findFirst().get();
    }

    public List<OrderResponse> getOrderResponse(String id) {
        List<OrderResponse> orderResponseList;
        Order order = findOrder(id);
        ResponseEntity<OrderDetail[]> orderDetails = orderDetailService.getOrderDetail(id);
        OrderDetail[] orderDetailsArr = orderDetails.getBody();
        orderResponseList = Arrays.stream(orderDetailsArr)
                .map(orderDetail -> {
                    Product product = productService.getProductDetail(orderDetail);
                    return new OrderResponse().builder()
                            .orderId(order.getOrderId())
                            .description(order.getDescription())
                            .productId(product.getId())
                            .productName(product.getName())
                            .unitPrice(product.getUnitPrice())
                            .quantity(orderDetail.getQuantity())
                            .total(product.getUnitPrice() * orderDetail.getQuantity())
                            .build();
                }).collect(Collectors.toList());
        return orderResponseList;
    }

}
