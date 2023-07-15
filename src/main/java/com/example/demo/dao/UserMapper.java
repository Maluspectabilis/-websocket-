package com.example.demo.dao;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<User> selectAll();

    @Insert("insert into user(username,password) values(#{username},#{password})")
    Integer addUser(String username,String password);

    /**
     * 匹配用户账号密码
     *
     * @param username
     * @param password
     * @return
     */
    @Select("select * from user where username = #{username} and password = #{password} limit 1")
    User selectUserByUsernameAndPassword(String username,String password);
}
