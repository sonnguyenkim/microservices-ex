package com.sn.ex.productservice.controller;

import com.netflix.discovery.EurekaClient;
import com.sn.ex.productservice.model.Product;
import com.sn.ex.productservice.service.ProductService;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("product")
public class ProductController {
    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private String portNumber;

    @Autowired
    private ProductService productService;

    @GetMapping("product/greeting")
    public String greeting() {
        System.out.println("Request received on port number " + portNumber);
        return String.format("Hello from '%s with Port Number %s'!", eurekaClient.getApplication(appName)
                .getName(), portNumber);
    }

    @GetMapping("product")
    public List<Product> getProductList() {
        return productService.getProductList();
    }

    @GetMapping("product/{id}")
    public Product findProduct(@PathVariable String id) {
        String report = String.format("Invoked from '%s with Port Number %s'!", eurekaClient.getApplication(appName)
                .getName(), portNumber);
        System.out.println(report);
        return productService.findProduct(id);
    }

}
