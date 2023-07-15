package com.example.demo.service.impl;

import com.example.demo.dao.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public Integer addUser(String username, String password) {
        int result = 0;
        try{
            if(username == null||password == null){
                System.out.println("注册失败，请填写用户名或密码");
                result=0;
            }else{
                System.out.println("注册成功");
                result = 1;
                userMapper.addUser(username,password);
            }
        }catch (Exception e){
            e.printStackTrace();//查询语句错误
        }
        return result;
    }

    @Override
    public Integer getUser(String username, String password) {
        int result = 0;//默认验证失败
        try{
            User user = userMapper.selectUserByUsernameAndPassword(username,password);
            if (user == null) {
                System.out.println("验证失败");
                result = 0;
            } else {
                System.out.println("验证成功");
                result = 1;
            }
        }catch (Exception e){
            e.printStackTrace();//查询语句错误
        }
        return result;
    }

}
