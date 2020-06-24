package com.sn.ex.orderservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sn.ex.orderservice.model.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderDetailService {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getOrderDetailFallback",
                commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "100"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
                }
    )
    public ResponseEntity<OrderDetail[]> getOrderDetail(String id) {
        return restTemplate.getForEntity("http://order-detail/orderdetail/"+id, OrderDetail[].class);
    }

    public ResponseEntity<OrderDetail[]> getOrderDetailFallback(String id) {
        OrderDetail[] orderDetails = {
                new OrderDetail().builder()
                    .orderId(Integer.parseInt(id))
                .productId(0)
                .quantity(0)
                .build()

        };

        return ResponseEntity.ok().body(orderDetails);
    }
}
