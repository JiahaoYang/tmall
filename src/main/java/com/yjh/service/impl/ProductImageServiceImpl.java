package com.yjh.service.impl;

import com.yjh.mapper.ProductImageMapper;
import com.yjh.pojo.ProductImage;
import com.yjh.pojo.ProductImageExample;
import com.yjh.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    private ProductImageMapper productImageMapper;

    @Override
    public int add(ProductImage pi) {
        return productImageMapper.insert(pi);
    }

    @Override
    public int delete(int id) {
        return productImageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(ProductImage pi) {
        return productImageMapper.updateByPrimaryKeySelective(pi);
    }

    @Override
    public ProductImage get(int id) {
        return productImageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ProductImage> list(int pid, String type) {
        ProductImageExample example = new ProductImageExample();
        example.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type);
        example.setOrderByClause("id");
        return productImageMapper.selectByExample(example);
    }
}
