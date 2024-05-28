package com.example.demo.annotion;

import java.lang.annotation.*;

/**
 * 是否需要判断表存在
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableExist {

    /**
     * 是否需要判断表存在
     */
    boolean value() default true;
}
