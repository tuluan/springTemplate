package com.tuluanix.service.system;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tuluanix.utils.Const;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuluanix.dao.UserJdbcDao;
import com.tuluanix.mybatis.dao.SysUserMapper;
import com.tuluanix.mybatis.domain.SysUser;

@Service
public class UserService {

    @Autowired
    private UserJdbcDao userDao;

    @Autowired
    private SysUserMapper sysUserMapper;

    public List<Map<String, Object>> getUserList(int pageNumber, int pageSize) {
        
//        List<Map<String, Object>> userList = userDao.getUserList(pageNumber, pageSize);
        List<Map<String, Object>> userList = userDao.login1();
        
        return userList;
    }
    
    public List<Map<String, Object>> selectUser() {
        List<Map<String, Object>> userList = sysUserMapper.selectUser();
        
        return userList;
    }
    
    @Transactional
    public Map<String, String> register(String loginName, String name, String password, String sex, int age) {
        Map<String, String> resultMap = new HashMap<String, String>();
        String salt = getSalt(32);
        password = getSHA256(salt + password);
        
//        boolean result = userDao.addUser(loginName, name, password, salt, sex, age);
        SysUser user = new SysUser();
//        user.setLoginname(loginName);
        user.setLoginname(Const.SCHEMA_LOCAL);
        user.setName(name);
        user.setPassword(password);
        user.setSalt(salt);
        user.setSex(sex);
        user.setAge(Byte.valueOf(""+age));
        int count = sysUserMapper.insertSelective(user);
        boolean result = count >= 1 ? true : false;
        resultMap.put("result", String.valueOf(result));
        
        return resultMap;
    }
    
    public Map<String, Object> userLogin(String userName, String password) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        String salt = userDao.getUserSalt(userName);
        
        if ("".equals(salt)) {
            resultMap.put("error", "无此用户");
        } else {
            password = getSHA256(salt + password);
            resultMap = userDao.login(userName, password);
            if (resultMap.isEmpty()) {
                resultMap.put("error", "密码错误");
            }
        }
        
        return resultMap;
    }
    
    private String getSHA256(String str) {
        String shaStr = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            try {
                md.update(str.getBytes("utf-8"));
                byte[] shaByte = md.digest();
                shaStr = Hex.encodeHexString(shaByte);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return shaStr;
    }
    
    private String getSalt(int length) {
        byte[] saltByte = new byte[length];
        new SecureRandom().nextBytes(saltByte);
        
        return Hex.encodeHexString(saltByte);
    }
}
