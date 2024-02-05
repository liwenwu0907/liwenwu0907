package com.example.demo.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class TestEntity {

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, max = 8, message = "密码长度为6-8位。")
    @Pattern(regexp = "[a-zA-Z]*", message = "密码不合法")
    private String password;

    @Range(max = 150, min = 1, message = "年龄范围应该在1-150内。")
    private Integer age;

//    @IsPhone(message = "phone不对")
//    private String phone;
//
//    @ValidateJfh(type = "jfid",message = "{key}:{value}格式不对")
//    private String jfid;

    @Email(message = "email格式不对")
    private String email;
}
