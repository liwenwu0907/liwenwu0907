package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.ParamsResource;
import com.example.demo.entity.RequestParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


@RestController
public class ApiCallTestController {

    @Autowired
    RestTemplate restTemplate;

    private static final String HOST = "http://61.160.107.107:81";

    /**
     * @description 获取用户Token
     * @author liwenwu
     * @time 2023/8/7
     */
    @RequestMapping("/user/token")
    public void getUserToken(){
        //userId必传
        String userId = "";
        //appId必传
        String appId = "";
        //这个分配appId的时候会给的（appSecret）
        String appSecret = "";
        //时间戳
        Long timestamp = System.currentTimeMillis();
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("userId",userId);
        queryMap.put("appId",appId);
        // 拼接入参字符串(方法：将appId、userId按序拼接（无间隔符），再依次拼接上appSecret、timestamp，获得HMAC-SHA256摘要值。)
        TreeMap<String, String> resultMap = new TreeMap<>(String::compareTo);
        resultMap.putAll(queryMap);
        String params = String.join(StringUtils.EMPTY, resultMap.values());
        String message = params + appSecret + timestamp;
        //签名
        String sign = HmacUtils.sha256(appSecret, message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        headers.add("sign", sign);
        headers.add("timestamp", String.valueOf(timestamp));
        Map<String,Object> param = new HashMap<>();
        param.put("userId",userId);
        param.put("appId",appId);
        ResponseEntity<String> response = restTemplate.exchange(
                HOST + "/system/api/system/user/token?userId={userId}&appId={appId}",
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                String.class,
                param);
        System.out.println("返回的结果：" + response.getBody());

    }


    /**
     * @description 获取系统Token
     * @author liwenwu
     * @time 2023/8/7
     */
    @RequestMapping("/token")
    public void getToken(){
        //appId必传
        String appId = "";
        //这个分配appId的时候会给的（appSecret）
        String appSecret = "";
        //时间戳
        Long timestamp = System.currentTimeMillis();
        // 拼接入参字符串(方法：将appId，再依次拼接上appSecret、timestamp，获得HMAC-SHA256摘要值。)
        String message = appId + appSecret + timestamp;
        //签名
        String sign = HmacUtils.sha256(appSecret, message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        headers.add("sign", sign);
        headers.add("timestamp", String.valueOf(timestamp));
        Map<String,Object> param = new HashMap<>();
        param.put("appId",appId);
        ResponseEntity<String> response = restTemplate.exchange(
                HOST + "/system/api/system/token?appId={appId}",
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                String.class,
                param);
        System.out.println("返回的结果：" + response.getBody());
    }

    /**
     * @description 获取用户基本信息
     * @author liwenwu
     * @time 2023/8/7
     */
    @RequestMapping("user/info")
    public void getUserInfo(){
        HttpHeaders headers = new HttpHeaders();
        //Token
        String token = "";
        //userId没有可不传
        String userId = "";
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authorization", token);
        Map<String,Object> param = new HashMap<>();
        param.put("userId",userId);
        param.put("appId","appId");
        ResponseEntity<String> response = restTemplate.exchange(
                HOST + "/system/api/user/info?userId={userId}&appId={appId}",
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                String.class,
                param);
        System.out.println("返回的结果：" + response.getBody());
    }

    /**
     * @description 用户同步接口
     * @author liwenwu
     * @time 2023/8/7
     */
    @RequestMapping("user/sync")
    public void userSync(){
        String url = HOST + "system/api/app/v1/sync";
        RequestParams requestParams = new RequestParams();
        //预置的参数(详细的类型请看文档)
        requestParams.setEventType("USER.DATA");
        //预置参数appId
        String appId = "00fbfddae8df4cceb9f67df40ee76935";
        //预置参数appKey（appSecret）
        String appKey = "e432828f2ae211eeb0fa0242ac110006";
        requestParams.setAppId(appId);
        ParamsResource paramsResource = new ParamsResource();
        //associatedData自己定义即可
        String associatedData = "XXX";
        paramsResource.setAssociatedData(associatedData);
        //json就是用户基本信息等
        String json = "{\n" +
                "\t\"userName\": \"minner\",\n" +
                "\t\"nickName\": \"minner\",\n" +
                "\t\"phone\": \"15950565722\",\n" +
                "\t\"email\": \"xxx@xxx.com\",\n" +
                "\t\"idCard\": \"321281202001011234\",\n" +
                "\t\"status\": \"0\",\n" +
                "\t\"groups\": [{\n" +
                "\t\t\"groupId\": 100,\n" +
                "\t\t\"groupName\": \"大数据管理局\",\n" +
                "\t\t\"groupCode\": \"BIG_DATA_DEPT\",\n" +
                "\t\t\"type\": \"dept\"\n" +
                "\t}, {\n" +
                "\t\t\"groupId\": 1001,\n" +
                "\t\t\"groupName\": \"阳明街道\",\n" +
                "\t\t\"groupCode\": \"YANGMING_STREET\",\n" +
                "\t\t\"type\": \"region\"\n" +
                "\t}],\n" +
                "\t\"roles\": [{\n" +
                "\t\t\"roleId\": \"c08afd62de2b43628dc0d67822c43e0e\",\n" +
                "\t\t\"roleName\": \"系统管理员\",\n" +
                "\t\t\"roleCode\": \"SYSTEM_ADMIN\",\n" +
                "\t\t\"permits\": [{\n" +
                "\t\t\t\"permitId\": \"xxx\",\n" +
                "\t\t\t\"permitName\": \"xxx\",\n" +
                "\t\t\t\"permitCode\": \"xxx\",\n" +
                "\t\t\t\"permitType\": \"xxx\",\n" +
                "\t\t\t\"path\": \"xxx\",\n" +
                "\t\t\t\"orderNum\": 1\n" +
                "\t\t}]\n" +
                "\t}]\n" +
                "}";
        String ciphertext = AESUtil.encrypt(json, appKey.getBytes(), associatedData.getBytes(), paramsResource.getNonce().getBytes());

        paramsResource.setCiphertext(ciphertext);
        requestParams.setResource(paramsResource);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> httpEntity = new HttpEntity<String>(JSON.toJSONString(requestParams), headers);
        String responseString = null;
        try {
            responseString = restTemplate.postForEntity(url,httpEntity,String.class).getBody();
            System.out.println("返回的结果：" + responseString);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
    }
}
