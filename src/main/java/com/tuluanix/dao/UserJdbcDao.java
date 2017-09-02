package com.tuluanix.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcDao {

    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    
    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate2;
    
    public List<Map<String, Object>> getUserList(int pageNumber, int pageSize) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM SYS_USER LIMIT ?,?");
        
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sb.toString(), pageNumber * pageSize -1, pageSize);
        
        return result;
    }

    public boolean addUser(String loginName, String name, String password, String salt, String sex, int age) {
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO SYS_USER (LOGINNAME, NAME, SEX, AGE, PASSWORD, SALT) VALUES (?, ?, ?, ?, ?, ?)");
        
        int i = jdbcTemplate.update(sb.toString(), loginName, name, sex, age, password, salt);
        
        System.out.println(i);
        
        return true;
    }
    
    public String getUserSalt(String loginName) {
        String salt = "";
        
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT SALT FROM SYS_USER WHERE LOGINNAME = ?");
        
        Map<String, Object> resultMap = jdbcTemplate.queryForMap(sb.toString(), loginName);
        
        if (!resultMap.isEmpty()) {
            salt = String.valueOf(resultMap.get("SALT"));
        }
        return salt;
    }
    
    public Map<String, Object> login(String loginName, String password) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM SYS_USER WHERE LOGINNAME = ? AND PASSWORD = ? ");
        
        Map<String, Object> result = jdbcTemplate.queryForMap(sb.toString(), loginName, password);
        
        return result;
    }
    
    public List<Map<String, Object>> login1() {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from help_topic");
        
        List<Map<String, Object>> result = jdbcTemplate2.queryForList(sb.toString());
        
        return result;
    }
}
