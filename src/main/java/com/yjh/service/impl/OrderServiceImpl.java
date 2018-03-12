package com.yjh.service.impl;

import com.yjh.mapper.OrderMapper;
import com.yjh.pojo.*;
import com.yjh.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderItemService orderItemService;

    @Override
    public int add(Order c) {
        return orderMapper.insert(c);
    }

    @Override
    public int delete(int id) {
        return orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Order c) {
        return orderMapper.updateByPrimaryKeySelective(c);
    }

    @Override
    public Order get(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    public List<Order> list() {
        OrderExample example = new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> result = orderMapper.selectByExample(example);
        setUser(result);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    public float add(Order order, List<OrderItem> orderItems) {
        float totalPrice = 0;
        add(order);
        for (OrderItem orderItem : orderItems) {
            orderItem.setOid(order.getId());
            orderItemService.update(orderItem);
            totalPrice += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
        }
        return totalPrice;
    }

    @Override
    public List<Order> list(int uid, String exclude) {
        OrderExample example = new OrderExample();
        example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(exclude);
        example.setOrderByClause("id");
        List<Order> orders = orderMapper.selectByExample(example);
        return orders;
    }

    public void setUser(List<Order> os) {
        for (Order o : os)
            setUser(o);
    }

    public void setUser(Order o) {
        int uid = o.getUid();
        User u = userService.get(uid);
        o.setUser(u);
    }
}
