package com.yjh.service;

import com.yjh.pojo.Order;
import com.yjh.pojo.OrderItem;

import java.util.List;

public interface OrderService {

    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    int add(Order c);

    int delete(int id);

    int update(Order c);

    Order get(int id);

    List<Order> list();

    float add(Order order, List<OrderItem> orderItems);

    List<Order> list(int uid, String exclude);
}