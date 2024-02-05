package com.example.demo.util.sentinel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sentinel")
public class SentinelAnnotationTest {

    @Autowired
    SentinelService sentinelService;

    @RequestMapping("annotationTest")
    public String annotationTest(long s){
        for(int i=0;i<10;i++){
            System.out.println(sentinelService.hello2222(s));
        }

        //throw new RuntimeException("getUserById command failed");
        return null;
    }

    @GetMapping("/hello/{name}")
    public String apiHello(@PathVariable String name) throws Exception {
        return sentinelService.sayHello(name);
    }

    // 对应的 `handleException` 函数需要位于 `ExceptionUtil` 类中，并且必须为 static 函数.
//    @SentinelResource(value = "test", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
//    public void test() {
//        System.out.println("Test");){
//    }


}
