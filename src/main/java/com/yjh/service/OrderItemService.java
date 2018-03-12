package com.yjh.service;

import com.yjh.pojo.*;

import java.util.List;

public interface OrderItemService {
    int add(OrderItem c);

    int delete(int id);

    int update(OrderItem c);

    OrderItem get(int id);

    List<OrderItem> list();

    void fill(List<Order> os);

    void fill(Order o);

    int getSaleCount(int pid);

    List<OrderItem> listByUser(int uid);

    OrderItem get(User user, int pid);

    int cartTotalCount(int uid);
}
