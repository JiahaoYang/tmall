package com.yjh.mapper;

import com.yjh.pojo.Category;
import com.yjh.util.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface CategoryMapper {
    @Select(" SELECT * FROM category ORDER BY id limit #{start}, #{count}")
    List<Category> list(Page page);

    @Select(" SELECT COUNT(*) FROM category ")
    int total();

    @Insert(" INSERT INTO category(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true)
    int add(Category category);
}
