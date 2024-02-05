package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * @author li wenwu
 * @date 2020/11/30 10:35
 */
public class PublicUtil {

    static Logger logger = LoggerFactory.getLogger(PublicUtil.class);

    public static <T> T getBeanByJsonObj(Object json, Class<T> tClass) {
        T t = null;

        try {
            if (json instanceof String) {
                t = JSON.parseObject(json.toString(), tClass);
            } else {
                t = JSON.parseObject(JSON.toJSONString(json), tClass);
            }
        } catch (Exception var4) {
            logger.error(var4.getMessage());
        }

        return t;
    }

    public static <T> List<T> getBeanListByJsonArray(Object obj, Class<T> tClass) {
        List t = null;

        try {
            t = JSON.parseArray(JSON.toJSONString(obj), tClass);
        } catch (Exception var4) {
            logger.error(var4.getMessage());
        }

        return t;
    }

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("projectStage","eeeeeeee");
        jsonObject.put("date",null);
        jsonObject.put("date2",new Date());
//        jsonObject.put("projectAmount",null);
        jsonObject.put("projectAmount2","ppp");
        try {
            System.out.println(jsonObject.getInteger("projectStage"));
        } catch (Exception e) {
            System.out.println("格式异常");
        }
        System.out.println(jsonObject.getDate("date"));
        System.out.println(jsonObject.getDate("date2"));
        System.out.println(jsonObject.getBigDecimal("projectAmount"));
        System.out.println(jsonObject.getBigDecimal("projectAmount2"));
    }
}
