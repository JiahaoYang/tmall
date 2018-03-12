package com.yjh.service;

import com.yjh.pojo.*;

import java.util.List;

public interface PropertyValueService {
    void init(Product product);
    int update(PropertyValue value);

    PropertyValue get(int productId, int propertyId);
    List<PropertyValue> list(int productId);
}
