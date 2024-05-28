package com.example.demo.controller;

import org.frameworkset.elasticsearch.boot.BBossESStarter;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.elasticsearch.entity.ESDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class ESTestController {

    @Autowired
    private BBossESStarter bBossESStarter;

    @GetMapping("/test")
    public void test(){
        String index = "alarm-record";
        String id = "1773593232408707072";
        ClientInterface clientInterface = bBossESStarter.getConfigRestClient("esmapper/alarm-record.xml");
        if(clientInterface.existIndice(index)){
            clientInterface.createIndiceMapping(index,"create");
        }
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("id",id);
        bBossESStarter.getRestClient().addDocument(paramMap);
        //删除索引(不存在会报错)
//        String result = bBossESStarter.getRestClient().dropIndice("blog");
//        System.out.println(result);
        System.out.println(bBossESStarter.getRestClient().getDocument(index,id));
        System.out.println(bBossESStarter.getRestClient().getDocument(index, "id",id));
        ESDatas<Object> list = bBossESStarter.getConfigRestClient("esmapper/alarm-record.xml").searchList(index + "/_search", "queryRecordList", paramMap , Object.class);
        System.out.println(list.getDatas());
    }

}
