package com.tuluanix.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuluanix.service.system.UserService;
import com.tuluanix.utils.StringUtil;

@Controller
@RequestMapping("/user")
public class UserManagerController {
    
    @Autowired
    private UserService userService;

    /**
     * 进入用户注册页面
     * @return
     */
    @RequestMapping("/register")
    public String register() {
        return "/user/userRegister";
    }
    
    /**
     * 用户注册
     * @param loginName
     * @param name
     * @param password
     * @param sex
     * @param age
     * @return
     */
    @RequestMapping("/doRegister")
    @ResponseBody
    public String doRegister(@RequestParam(value="loginName")String loginName,
            @RequestParam(value="name")String name,
            @RequestParam(value="password")String password,
            @RequestParam(value="sex")String sex,
            @RequestParam(value="age")int age){
        Map<String, String> resultMap = userService.register(loginName, name, password, sex, age);
        return StringUtil.convertToJsonData(resultMap);
    }
    
    /**
     * 进入登录页面
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "user/userLogin";
    }
    
    /**
     * 用户登录
     * @param model
     * @param userName
     * @param passWord
     * @return
     */
    @RequestMapping("doLogin")
    public String userLogin(Model model, @RequestParam(value="userName")String userName, @RequestParam(value="passWord")String passWord) {
        Map<String, Object> userMap = userService.userLogin(userName, passWord);
        
        model.addAttribute("uName", userName);
        model.addAttribute("uPassWord", passWord);
        model.addAttribute("info", StringUtil.convertToJsonData(userMap));
        
        return "user/loginResult";
    }
    
//    @RequestMapping(value="getUser", produces="text/plain;charset=utf-8")
    @RequestMapping("getUser")
    @ResponseBody
    public String getUser(@RequestParam(value="pageNumber")int pageNumber, @RequestParam(value="pageSize")int pageSize) {
        List<Map<String, Object>> userList = userService.getUserList(pageNumber, pageSize);
        
        return StringUtil.convertToJsonData(userList);
    }
}
