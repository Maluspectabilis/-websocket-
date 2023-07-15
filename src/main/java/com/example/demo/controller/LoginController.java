package com.example.demo.controller;


import com.example.demo.pojo.ChatMessage;
import com.example.demo.pojo.User;
import com.example.demo.service.ChatMessageService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/keshe")
public class LoginController {

    @Resource
    private UserService userService;

    @Resource
    ChatMessageService chatMessageService;

    /**
     * 登录页面接口：
     * 返回登录页面
     * 登录失败重返页面时能提示登陆失败信息
     * @param model
     * @return
     */
    @RequestMapping("/loginInit")
    public String loginInit(Model model){
        model.addAttribute("msg","请先登录或注册");//这里是需要渲染到登录页面的信息
        return "login";//提取login“模板”，将数据按照thymeleaf语法
    }

    @RequestMapping("/registerInit")
    public String regidterInit(){
        return "register";
    }
    @PostMapping("/userLogin")//检测账号密码
    public String userLogin(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession httpSession) {
        System.out.println("登录验证中...");
        /*这里要进行service业务操作：判断账号密码是否正确。根据service返回值来进行相应页面跳转。当账号验证失败跳转回登录页面，否则进入主页面*/
        int result = userService.getUser(username,password);
        if(result == 1){
            //登录验证通过，跳转到主页面，同时将账号密码放置于session中方便以后操作中提取
            httpSession.setAttribute("username",username);
            httpSession.setAttribute("password",password);
            model.addAttribute("verification", "true");

            List<User> users = userService.selectAll();
            model.addAttribute("users",users);
            return "chatRoom";
        }else{
            //用户不存在，登录失败：返回登录页面并提示相关信息
            model.addAttribute("verification", "false");//跳转到login并提示信息
            model.addAttribute("msg", "登录失败，请检查用户名/密码");
            return "login";
        }
    }


    @RequestMapping("/register")
    public String register(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession httpSession){
        int result = userService.addUser(username,password);
        if(result == 1){
            httpSession.setAttribute("username",username);
            httpSession.setAttribute("password",password);
            model.addAttribute("verification", "true");
            model.addAttribute("msg", "注册成功,请登录");
            return "login";
        }else{
            model.addAttribute("verification", "false");
            model.addAttribute("msg", "注册失败，请填写用户名/密码");
            return "register";
        }
    }

    /**
     * 获取聊天历史记录
     *
     * @param username
     * @param toUserName
     * @return
     */
    @RequestMapping("getMessage")
    @ResponseBody
    public Map getMessage(@RequestParam("username") String username, @RequestParam("toUserName") String toUserName){
        Map<String, Object> map = new HashMap<>();
        try{
            List<ChatMessage> chatMessages = chatMessageService.getMessageHistory(username,toUserName);
            map.put("data",chatMessages);
        }catch (Exception e){
            e.printStackTrace();
        }
        map.put("code", 0);
        map.put("msg", "历史记录获取成功");
        return map;
    }

}
