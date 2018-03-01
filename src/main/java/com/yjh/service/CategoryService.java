package com.yjh.service;

import com.yjh.pojo.Category;
import com.yjh.util.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {

    List<Category> list(Page page);
    int total();
}
