package com.yjh.mapper;

import com.yjh.pojo.Category;
import com.yjh.util.Page;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface CategoryMapper {
    @Select(" SELECT * FROM category ORDER BY id limit #{current}, #{count}")
    List<Category> list(Page page);

    @Select(" SELECT COUNT(*) FROM category ")
    int total();
}
