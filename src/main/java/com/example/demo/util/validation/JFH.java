package com.example.demo.util.validation;

import java.lang.annotation.*;

/**
 * JFH自定义注解
 * @author  liwenwu
 * @date  2021/10/26
 **/
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JFH {

    //类型，如手机号，email，jfid,buid等
    String type();

    //验证不通过的提示信息
    String message();

    //是否必填

}
