package com.yjh.service.impl;

import com.yjh.mapper.PropertyValueMapper;
import com.yjh.pojo.*;
import com.yjh.service.PropertyService;
import com.yjh.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    private PropertyValueMapper propertyValueMapper;
    @Autowired
    private PropertyService propertyService;

    @Override
    public void init(Product product) {
        List<Property> properties = propertyService.list(product.getCid());
        for (Property property : properties) {
            PropertyValue value = get(property.getId(), product.getId());
            if (null == value) {
                value = new PropertyValue();
                value.setPtid(property.getId());
                value.setPid(product.getId());
                propertyValueMapper.insertSelective(value);
            }
        }
    }

    @Override
    public int update(PropertyValue value) {
        return propertyValueMapper.updateByPrimaryKeySelective(value);
    }

    /**
     * @param ptid: 属性id
     * @param pid:  产品id
     * @return
     */
    @Override
    public PropertyValue get(int ptid, int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPtidEqualTo(ptid).andPidEqualTo(pid);
        example.setOrderByClause("id");
        List<PropertyValue> values = propertyValueMapper.selectByExample(example);
        if (!values.isEmpty())
            return values.get(0);
        return null;
    }

    @Override
    public List<PropertyValue> list(int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id");
        List<PropertyValue> values = propertyValueMapper.selectByExample(example);
        for (PropertyValue value : values) {
            Property property = propertyService.get(value.getPtid());
            value.setProperty(property);
        }
        return values;
    }
}
