package com.sn.ex.orderservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sn.ex.orderservice.model.OrderDetail;
import com.sn.ex.orderservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductService {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getProductDetailFallback")
    public Product getProductDetail(OrderDetail orderDetail) {

//        ResponseEntity<Product> responseEntity = restTemplate.getForEntity(
//                "http://product-info/product/"+ orderDetail.getProductId().toString(), Product.class);


        return restTemplate.getForObject("http://product-info/product/"+ orderDetail.getProductId().toString(), Product.class);
    }

    public Product getProductDetailFallback(OrderDetail orderDetail) {
        return new Product().builder()
                .id(orderDetail.getProductId())
                .name("Unknown")
                .quantity(0)
                .unitPrice(0.0)
                .build();
    }
}
