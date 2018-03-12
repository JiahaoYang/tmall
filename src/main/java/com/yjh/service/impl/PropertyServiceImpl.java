package com.yjh.service.impl;

import com.yjh.mapper.PropertyMapper;
import com.yjh.pojo.Property;
import com.yjh.pojo.PropertyExample;
import com.yjh.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService{

    @Autowired
    private PropertyMapper propertyMapper;

    @Override
    public int add(Property property) {
        return propertyMapper.insert(property);
    }

    @Override
    public int delete(int id) {
        return propertyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Property property) {
        return propertyMapper.updateByPrimaryKeySelective(property);
    }

    @Override
    public Property get(int id) {
        return propertyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Property> list(int cid) {
        PropertyExample example = new PropertyExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id");
        return propertyMapper.selectByExample(example);
    }
}
