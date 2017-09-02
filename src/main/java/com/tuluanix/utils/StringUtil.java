package com.tuluanix.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StringUtil {

    /**
     * 将数据转换成json格式
     * 
     * @param list 需要转化的数据
     * @return json格式的数据
     */
    public static String convertToJsonData(Object list) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            String liststr = mapper.writeValueAsString(list);
            return liststr;
        } catch (JsonProcessingException jsone) {
            throw new RuntimeException(jsone);
        }
    }
}
