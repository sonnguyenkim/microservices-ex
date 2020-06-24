package com.sn.ex.orderdetailservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
}
