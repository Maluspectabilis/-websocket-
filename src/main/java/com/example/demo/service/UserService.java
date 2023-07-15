package com.example.demo.service;

import com.example.demo.pojo.User;

import java.util.List;

public interface UserService {
    public List<User> selectAll();
    public Integer addUser(String username,String password);

    /**
     * 登录验证业务：根据账号密码查询数据库中是否存在账户。
     * 当查询到用户时，表示登录验证通过返回1，当查询不到用户时，表示该用户未注册，返回0
     *
     * @param username
     * @param password
     * @return
     */
    public Integer getUser(String username,String password);
}
