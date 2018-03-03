package com.yjh.mapper;

import com.yjh.pojo.Category;
import com.yjh.util.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface CategoryMapper {
/*    @Select(" SELECT * FROM category ORDER BY id limit #{start}, #{count}")
    List<Category> list(Page page);

    @Select(" SELECT COUNT(*) FROM category ")
    int total();*/

    @Select(" SELECT * FROM category ORDER BY id ")
    List<Category> list();


    @Insert(" INSERT INTO category(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true)
    int add(Category category);

    @Delete(" DELETE FROM category where id=#{id} ")
    int delete(int id);

    @Select(" SELECT * FROM category WHERE id=#{id}")
    Category get(int id);

    @Update(" UPDATE category SET name=#{name} WHERE id=#{id} ")
    int update(Category category);
}

class Cagegory {
    private Integer id;
    private String name;
}

class product {
    private Integer id;
    private String name;
    private Float price;
}