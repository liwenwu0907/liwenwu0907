package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("qyweixinBot")
@Slf4j
public class QYWeiXinRobotDemoController {

    @Autowired
    RestTemplate restTemplate;

    private static final String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=33197a66-dd4a-44b9-8cfc-40e8cff7af98";

    @PostMapping("sendMessage2")
    @ResponseBody
    public void sendMessage(@RequestBody JSONObject jsonObject) {
        String content = "";
        if (!jsonObject.containsKey("object_kind")) {
            throw new RuntimeException("无法判断提交类型，object_kind不存在");
        }
        if (!jsonObject.containsKey("after")) {
            throw new RuntimeException("没有提交签名，after不存在");
        }
        if (!jsonObject.containsKey("user_name")) {
            throw new RuntimeException("没有姓名，user_name不存在");
        }
        if (!jsonObject.containsKey("project")) {
            throw new RuntimeException("没有项目信息，project不存在");
        }
        //提交类型
        String objectKind = jsonObject.getString("object_kind");
        //提交签名
        String after = jsonObject.getString("after");
        //姓名
        String userName = jsonObject.getString("user_name");
        JSONObject projectObject = jsonObject.getJSONObject("project");
        if (!projectObject.containsKey("git_http_url")) {
            throw new RuntimeException("没有git地址，git_http_url不存在");
        }
        if (!projectObject.containsKey("namespace")) {
            throw new RuntimeException("没有项目名称，namespace不存在");
        }
        //git地址
        String gitHttpUrl = projectObject.getString("git_http_url");
        //项目名称
        String namespace = projectObject.getString("namespace");
        if (!jsonObject.containsKey("commits")) {
            throw new RuntimeException("没有提交信息，commits不存在");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(userName).append(objectKind).append("了").append(namespace).append("代码。")
                .append("代码git地址为：").append(gitHttpUrl).append(",提交的签名为：").append(after).append("。具体如下：")
                .append(System.getProperty("line.separator"));
        JSONArray commitArray = jsonObject.getJSONArray("commits");
        if (commitArray.size() > 0) {
            StringBuilder sb = new StringBuilder();
            //遍历获取提交的title
            for (int i = 0; i < commitArray.size(); i++) {
                JSONObject commitObject = commitArray.getJSONObject(i);
                if (!commitObject.containsKey("title")) {
                    throw new RuntimeException("没有提交标题，title不存在");
                }
                if (!commitObject.containsKey("timestamp")) {
                    throw new RuntimeException("没有提交时间，timestamp不存在");
                }
                if (!commitObject.containsKey("author")) {
                    throw new RuntimeException("没有作者，author不存在");
                }
                String title = commitObject.getString("title");
                String timestamp = commitObject.getString("timestamp");
                JSONObject authorObject = commitObject.getJSONObject("author");
                if (!authorObject.containsKey("name")) {
                    throw new RuntimeException("没有修改人，name不存在");
                }
                String name = authorObject.getString("name");
                sb.append(name).append("于").append(timestamp).append("修改了").append(title).append(System.getProperty("line.separator"));
            }
            stringBuilder.append(sb);
        }
        content = stringBuilder.toString();
        JSONObject messageObject = new JSONObject();
        messageObject.put("msgtype", "text");
        JSONObject textObject = new JSONObject();
        textObject.put("content", content);
        textObject.put("mentioned_list", new String[]{"@all"});
        messageObject.put("text", textObject);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(messageObject), headers);
        String responseString = null;
        try {
            responseString = restTemplate.postForEntity(url, httpEntity, String.class).getBody();
            log.info("发送企业微信的返回结果：{}", responseString);
        } catch (RestClientException e) {
            throw new RuntimeException("发送企业微信消息失败。", e);
        }


    }

    @PostMapping("sendMessage")
    @ResponseBody
    public void sendMessage(@RequestPart("file") MultipartFile file) {
        if (null != file) {
            String fileName = file.getOriginalFilename();
            if (StringUtils.isNotBlank(fileName) && fileName.endsWith(".json")) {
                String jsonStr = readJsonFile(file);
                String content = "";
                JSONObject jsonObject = JSON.parseObject(jsonStr);
                if (!jsonObject.containsKey("object_kind")) {
                    throw new RuntimeException("无法判断提交类型，object_kind不存在");
                }
                if (!jsonObject.containsKey("after")) {
                    throw new RuntimeException("没有提交签名，after不存在");
                }
                if (!jsonObject.containsKey("user_name")) {
                    throw new RuntimeException("没有姓名，user_name不存在");
                }
                if (!jsonObject.containsKey("project")) {
                    throw new RuntimeException("没有项目信息，project不存在");
                }
                //提交类型
                String objectKind = jsonObject.getString("object_kind");
                //提交签名
                String after = jsonObject.getString("after");
                //姓名
                String userName = jsonObject.getString("user_name");
                JSONObject projectObject = jsonObject.getJSONObject("project");
                if (!projectObject.containsKey("git_http_url")) {
                    throw new RuntimeException("没有git地址，git_http_url不存在");
                }
                if (!projectObject.containsKey("namespace")) {
                    throw new RuntimeException("没有项目名称，namespace不存在");
                }
                //git地址
                String gitHttpUrl = projectObject.getString("git_http_url");
                //项目名称
                String namespace = projectObject.getString("namespace");
                if (!jsonObject.containsKey("commits")) {
                    throw new RuntimeException("没有提交信息，commits不存在");
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(userName).append(objectKind).append("了").append(namespace).append("代码。")
                        .append("代码git地址为：").append(gitHttpUrl).append(",提交的签名为：").append(after).append("。具体如下：")
                        .append(System.getProperty("line.separator"));
                JSONArray commitArray = jsonObject.getJSONArray("commits");
                if (commitArray.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    //遍历获取提交的title
                    for (int i = 0; i < commitArray.size(); i++) {
                        JSONObject commitObject = commitArray.getJSONObject(i);
                        if (!commitObject.containsKey("title")) {
                            throw new RuntimeException("没有提交标题，title不存在");
                        }
                        if (!commitObject.containsKey("timestamp")) {
                            throw new RuntimeException("没有提交时间，timestamp不存在");
                        }
                        if (!commitObject.containsKey("author")) {
                            throw new RuntimeException("没有作者，author不存在");
                        }
                        String title = commitObject.getString("title");
                        String timestamp = commitObject.getString("timestamp");
                        JSONObject authorObject = commitObject.getJSONObject("author");
                        if (!authorObject.containsKey("name")) {
                            throw new RuntimeException("没有修改人，name不存在");
                        }
                        String name = authorObject.getString("name");
                        sb.append(name).append("于").append(timestamp).append("修改了").append(title).append(System.getProperty("line.separator"));
                    }
                    stringBuilder.append(sb);
                }
                content = stringBuilder.toString();
                JSONObject messageObject = new JSONObject();
                messageObject.put("msgtype", "text");
                JSONObject textObject = new JSONObject();
                textObject.put("content", content);
                textObject.put("mentioned_list", new String[]{"@all"});
                messageObject.put("text", textObject);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(messageObject), headers);
                String responseString = null;
                try {
                    responseString = restTemplate.postForEntity(url, httpEntity, String.class).getBody();
                    log.info("发送企业微信的返回结果：{}", responseString);
                } catch (RestClientException e) {
                    throw new RuntimeException("发送企业微信消息失败。", e);
                }
            } else {
                throw new RuntimeException("文件解析错误，非json文件");
            }
        }
    }


    private String readJsonFile(MultipartFile jsonFile) {
        String jsonStr = "";
        if (null != jsonFile) {
            StringBuilder sb = null;
            Reader reader = null;
            try {
                reader = new InputStreamReader(jsonFile.getInputStream(), StandardCharsets.UTF_8);
                int ch = 0;
                sb = new StringBuilder();
                while ((ch = reader.read()) != -1) {
                    sb.append((char) ch);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != reader) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != sb) {
                jsonStr = sb.toString();
            }

        }
        return jsonStr;
    }

}
