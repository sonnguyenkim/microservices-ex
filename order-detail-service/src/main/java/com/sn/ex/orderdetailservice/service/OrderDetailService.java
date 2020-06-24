package com.sn.ex.orderdetailservice.service;

import com.sn.ex.orderdetailservice.model.OrderDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailService {
    private List<OrderDetail> orderDetailList = new ArrayList<>();

    OrderDetailService() {
        orderDetailList.add(new OrderDetail(1,1,5));
        orderDetailList.add(new OrderDetail(1,2,10));
        orderDetailList.add(new OrderDetail(1,3,15));

        orderDetailList.add(new OrderDetail(2,2,10));
        orderDetailList.add(new OrderDetail(2,4,7));

        orderDetailList.add(new OrderDetail(3,1,5));
        orderDetailList.add(new OrderDetail(3,2,5));
        orderDetailList.add(new OrderDetail(3,3,5));
        orderDetailList.add(new OrderDetail(3,4,5));

        orderDetailList.add(new OrderDetail(4,2,7));
        orderDetailList.add(new OrderDetail(4,3,8));
        orderDetailList.add(new OrderDetail(4,4,5));

    }

    public List<OrderDetail> getOrderDetailByOrderId(String id) {
        return orderDetailList.stream()
                .filter(orderDetail -> {
                    return orderDetail.getOrderId().equals(Integer.parseInt(id));
                }).collect(Collectors.toList());
    }
}
