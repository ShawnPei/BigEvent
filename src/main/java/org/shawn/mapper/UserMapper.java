package org.shawn.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.shawn.pojo.User;

@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User findByUserName(String username);

    @Insert("insert into user(username, password, create_time, update_time) " +
            "VALUES (#{username}, #{encryptedPwd},now(),now())")
    void add(String username, String encryptedPwd);

    @Update("UPDATE user set nickname = #{nickname}, email = #{email}, update_time = #{updateTime} where id = #{id}")
    void update(User user);

    @Update("UPDATE user set user_pic = #{avatarUrl}, update_time=now(), id = #{id}")
    void updateAvatar(String avatarUrl, Integer id);

    @Update("update user set password = #{md5String},update_time=now() where id = #{id}")
    void updatePwd(String md5String, Integer id);
}
