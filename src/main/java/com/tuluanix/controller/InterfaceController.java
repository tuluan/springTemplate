package com.tuluanix.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuluanix.service.system.UserService;
import com.tuluanix.utils.StringUtil;

@Controller
@RequestMapping("/interface")
public class InterfaceController {

    @Autowired
    private UserService userService;
    
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
    
    @RequestMapping("/selectUser")
    @ResponseBody
    public String selectUser() {
        
        List<Map<String, Object>> userList = userService.selectUser();
        return StringUtil.convertToJsonData(userList);
    }
}
