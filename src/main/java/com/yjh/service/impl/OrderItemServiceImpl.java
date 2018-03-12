package com.yjh.service.impl;

import com.yjh.mapper.OrderItemMapper;
import com.yjh.pojo.*;
import com.yjh.service.OrderItemService;
import com.yjh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService{

    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductService productService;

    @Override
    public int add(OrderItem c) {
       return orderItemMapper.insert(c);
    }

    @Override
    public int delete(int id) {
        return orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(OrderItem c) {
        return orderItemMapper.updateByPrimaryKeySelective(c);
    }

    @Override
    public OrderItem get(int id) {
        OrderItem orderItem =  orderItemMapper.selectByPrimaryKey(id);
        setProduct(orderItem);
        return orderItem;
    }

    @Override
    public List<OrderItem> list() {
        OrderItemExample example = new OrderItemExample();
        example.setOrderByClause("id");
        return orderItemMapper.selectByExample(example);
    }

    /**
     * 订单与订单项的一对多关系
     */
    @Override
    public void fill(List<Order> os) {
        for (Order order : os)
            fill(order);
    }

    @Override
    public void fill(Order o) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andOidEqualTo(o.getId());
        example.setOrderByClause("id");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        setProduct(orderItems);

        float total = 0;
        int totalNum = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
            totalNum += orderItem.getNumber();
        }
        o.setTotal(total);
        o.setOrderItems(orderItems);
        o.setTotalNum(totalNum);
    }

    @Override
    public int getSaleCount(int pid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andPidEqualTo(pid);
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        int result = 0;
        for (OrderItem orderItem : orderItems)
            result += orderItem.getNumber();
        return result;
    }

    @Override
    public List<OrderItem> listByUser(int uid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andUidEqualTo(uid).andOidIsNull();
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        setProduct(orderItems);
        return orderItems;
    }

    @Override
    public OrderItem get(User user, int pid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andUidEqualTo(user.getId()).andPidEqualTo(pid);
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        setProduct(orderItems);
        return orderItems.isEmpty() ? null : orderItems.get(0);
    }

    @Override
    public int cartTotalCount(int uid) {
        List<OrderItem> orderItems = listByUser(uid);
        int totalCount = 0;
        for (OrderItem orderItem : orderItems)
            totalCount += orderItem.getNumber();
        return totalCount;
    }

    private void setProduct(OrderItem orderItem) {
        orderItem.setProduct(productService.get(orderItem.getPid()));
    }

    private void setProduct(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems)
            setProduct(orderItem);
    }
}
