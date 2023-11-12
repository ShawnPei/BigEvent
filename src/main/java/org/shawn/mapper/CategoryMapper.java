package org.shawn.mapper;

import org.apache.ibatis.annotations.*;
import org.shawn.pojo.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("select * from category where create_user = #{userId}")
    List<Category> list(Integer userId);

    @Select("select * from category where category_name = #{categoryName}")
    Category getByCategoryName(String categoryName);

    @Insert("INSERT into category(category_name, category_alias,create_user,create_time,update_time)" +
            "values (#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void add(Category category);

    @Update("update category set category_name = #{categoryName}, category_alias = #{categoryAlias},update_time=now() where id = #{id}")
    void update(Category category);

    @Select("select * from category where id = #{id}")
    Category findById(Integer id);

    @Delete("delete from category where id = #{id};")
    void deleteById(Integer id);
}
