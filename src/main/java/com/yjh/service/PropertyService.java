package com.yjh.service;

import com.yjh.pojo.Property;

import java.util.List;

public interface PropertyService {
    int add(Property property);
    int delete(int id);
    int update(Property property);
    Property get(int id);
    List<Property> list(int cid);
}
