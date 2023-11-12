package org.shawn.mapper;


import org.apache.ibatis.annotations.*;
import org.shawn.pojo.Article;

import java.awt.*;
import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("insert into article(title, content, cover_img, state, category_id, create_user, create_time, update_time) " +
            "VALUES(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime}) ")
    void add(Article article);

    @Update("update article set title = #{title}, content = #{content}, cover_img = #{coverImg}, state=#{state},category_id=#{categoryId} where id=#{id}")
    void update(Article article);

    @Select("select * from article where id = #{id}")
    Article getById(Integer id);

    @Delete("delete from article where id = #{id};")
    void deleteById(Integer id);

    List<Article> list(Integer categoryId, String state, Integer userId);
}
