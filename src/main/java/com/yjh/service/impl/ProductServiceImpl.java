package com.yjh.service.impl;

import com.yjh.mapper.ProductMapper;
import com.yjh.pojo.*;
import com.yjh.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ReviewService reviewService;

    @Override
    public int add(Product product) {
        return productMapper.insert(product);
    }

    @Override
    public int delete(int id) {
        return productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Product product) {
        return productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public Product get(int id) {
        Product product = productMapper.selectByPrimaryKey(id);
        setFirstProductImage(product);
        setCategory(product);
        return product;
    }

    @Override
    public List<Product> list(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id");
        List<Product> products = productMapper.selectByExample(example);
        setFirstProductImage(products);
        setCategory(products);
        return products;
    }

    @Override
    public void setFirstProductImage(Product product) {
        List<ProductImage> images = productImageService.list(product.getId(),
                ProductImageService.type_single);
        if (!images.isEmpty()) {
            product.setFirstProductImage(images.get(0));
        }
    }

    @Override
    public void fill(Category category) {
        List<Product> products = list(category.getId());
        category.setProducts(products);
    }

    /**
     * 将分类下的产品集合拆成每行8个
     */
    @Override
    public void fillByRow(List<Category> categories) {
        int rowSize = 8;
        for (Category category : categories) {
            List<Product> products =category.getProducts();
            List<List<Product>> productsByRow = new ArrayList<>();
            for (int i = 0; i < products.size(); i += rowSize) {
                int end = i + rowSize;
                end = end > products.size() ? products.size() : end;
                List<Product> productEachRow = products.subList(i, end);
                productsByRow.add(productEachRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }

    @Override
    public void setSaleAndReview(Product product) {
        int saleCount = orderItemService.getSaleCount(product.getId());
        int reviewCount = reviewService.getReviewCount(product.getId());
        product.setSaleCount(saleCount);
        product.setReviewCount(reviewCount);
    }

    @Override
    public void setSaleAndReview(List<Product> products) {
        for (Product product : products)
            setSaleAndReview(product);
    }

    @Override
    public List<Product> search(String keyword) {
        ProductExample example = new ProductExample();
        example.createCriteria().andNameLike("%" + keyword + "%");
        example.setOrderByClause("id");
        List<Product> products = productMapper.selectByExample(example);
        setCategory(products);
        setSaleAndReview(products);
        setFirstProductImage(products);
        return products;
    }

    @Override
    public void fill(List<Category> categories) {
        for (Category category : categories)
            fill(category);
    }

    public void setFirstProductImage(List<Product> products) {
        for (Product product : products)
            setFirstProductImage(product);
    }

    public void setCategory(Product product) {
        Category category = categoryService.get(product.getCid());
        product.setCategory(category);
    }

    public void setCategory(List<Product> products) {
        for (Product product : products)
            setCategory(product);
    }


}
