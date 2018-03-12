package com.yjh.service;

import com.yjh.pojo.Category;
import com.yjh.pojo.Product;

import java.util.List;

public interface ProductService {
    int add(Product product);

    int delete(int id);

    int update(Product product);

    Product get(int id);

    List<Product> list(int cid);

    void setFirstProductImage(Product product);

    void fill(Category category);

    void fill(List<Category> categories);

    void fillByRow(List<Category> categories);

    void setSaleAndReview(Product product);

    void setSaleAndReview(List<Product> products);

    List<Product> search(String keyword);
}
