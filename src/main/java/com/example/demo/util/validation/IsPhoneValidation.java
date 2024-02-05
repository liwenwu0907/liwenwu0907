package com.example.demo.util.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class IsPhoneValidation implements ConstraintValidator<IsPhone,String> {

    private static final Pattern PATTERN = Pattern.compile( "^1[3456789]\\d{9}$");

    public static void main(String[] args) {
        String phone = "19313969261";
        System.out.println(PATTERN.matcher(phone).matches());
    }

    /**
     * 验证手机号(不为空才判断，不为空则不验证)
     */
    public static boolean isPhone(String iphone){
        if (StringUtils.isNotBlank(iphone)) {
            return PATTERN.matcher(iphone).matches();
        }
        return true;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        return isPhone(s);
    }

    @Override
    public void initialize(IsPhone constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
