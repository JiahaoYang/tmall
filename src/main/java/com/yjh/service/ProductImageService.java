package com.yjh.service;

import com.yjh.pojo.ProductImage;

import java.util.List;

public interface ProductImageService {

    String type_single = "type_single";
    String type_detail = "type_detail";

    int add(ProductImage pi);

    int delete(int id);

    int update(ProductImage pi);

    ProductImage get(int id);

    List<ProductImage> list(int pid, String type);

}
