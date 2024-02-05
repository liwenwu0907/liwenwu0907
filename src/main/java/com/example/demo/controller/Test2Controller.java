//package com.example.demo.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.jfh.ms.cmm.tenant.entity.TestEntity;
//import com.jfh.validation.annotation.ValidateJfh;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
//
//@RestController
//@RequestMapping(value = "/api")
//@Validated
//public class Test2Controller {
//
//    @RequestMapping(value = "/test/validate")
//    public void testValidate(
//            @RequestBody(required = false) @Validated TestEntity testEntity,
//             @NotNull(message = "id不能为空")  Integer id,
//             @NotBlank(message = "string不能为空")  String oop,
//            @NotBlank(message = "jfid不能为空") @ValidateJfh(type = "jfid",message = "jfid格式不对") String jfid){
//        System.out.println(JSON.toJSONString(testEntity));
//        System.out.println(id);
//        System.out.println(oop);
//    }
//
//    @RequestMapping(value = "/jfh/validate")
//    public void jfhValidate(
//            @RequestBody(required = false) @Validated TestEntity testEntity){
//        System.out.println(JSON.toJSONString(testEntity));
//    }
//}
