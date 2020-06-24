package com.sn.ex.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Integer orderId;
    private String description;
    private Integer productId;
    private String productName;
    private Double unitPrice;
    private Integer quantity;
    private Double total;

}
