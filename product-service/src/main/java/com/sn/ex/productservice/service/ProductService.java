package com.sn.ex.productservice.service;

import com.sn.ex.productservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private List<Product> productList;

    ProductService() {
        productList = new ArrayList<>();
        productList.add(
                new Product().builder()
                    .id(1)
                    .name("Product 1")
                    .quantity(100)
                    .unitPrice(11.50).build());
        productList.add(
                new Product().builder()
                        .id(2)
                        .name("Product 2")
                        .quantity(200)
                        .unitPrice(21.50).build());
        productList.add(
                new Product().builder()
                        .id(3)
                        .name("Product 3")
                        .quantity(150)
                        .unitPrice(17.50).build());
        productList.add(
                new Product().builder()
                        .id(4)
                        .name("Product 4")
                        .quantity(340)
                        .unitPrice(41.50).build()
                );
    }

    public List<Product> getProductList() {
        return productList;
    }

    public Product findProduct(String id) {
        return productList.stream().filter(product -> {
            return product.getId().equals(Integer.parseInt(id));
        }).findFirst().get();
    }


}
