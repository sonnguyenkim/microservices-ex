package com.sn.ex.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
//@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailList {
    private List<OrderDetail> orderDetailList;

    public OrderDetailList() {
        orderDetailList = new ArrayList<>();
    }
}
