//package com.example.demo.service;
//
//import com.example.demo.dao.MasterSlaveDAO;
//import com.example.demo.entity.ApiUser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author li wenwu
// * @date 2020/12/22 13:39
// */
//@Service
//public class MasterSlaveService {
//
//    @Autowired
//    MasterSlaveDAO masterSlaveDAO;
//
//    /**
//     * 获取数据
//     * @param
//     * @return
//     */
//    public List<Map> getData(){
//        List<Map> list = masterSlaveDAO.getData();
//        return list;
//    }
//
//    /**
//     * 插入数据
//     * @param
//     * @return
//     */
//    public void saveData(){
//        Map map = new HashMap();
//        map.put("id", System.currentTimeMillis());
//        map.put("email","email");
//        map.put("nick_name","nick_name");
//        map.put("pass_word","pass_word");
//        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime date2 = LocalDateTime.now();
//        map.put("reg_time", date2.format(newFormatter));
//        map.put("user_name","user_name");
//        map.put("is_deleted","1");
//        masterSlaveDAO.saveData(map);
//    }
//}
