//package com.example.demo.util.validation;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
//import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//import java.util.Map;
//import java.util.regex.Pattern;
//
//@Slf4j
//public class ValidateJfhValidation implements ConstraintValidator<ValidateJfh,String> {
//
//    /**
//     * 通用验证方法(不为空才判断，不为空则不验证)
//     */
//    public static boolean common(String s,String patternString){
//        if (StringUtils.isNotBlank(s)) {
//            Pattern pattern = Pattern.compile(patternString);
//            return pattern.matcher(s).matches();
//        }
//        return true;
//    }
//
//    @Override
//    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
//        //首先获取type
//        Map map = ((ConstraintDescriptorImpl) ((ConstraintValidatorContextImpl) constraintValidatorContext).getConstraintDescriptor()).getAnnotationDescriptor().getAttributes();
//        String type = "";
//        try {
//            type = map.get("type").toString();
//        } catch (Exception e) {
//            log.error("没有配置type",e);
//        }
//        if(StringUtils.isNoneEmpty(type,s)){
//            //这里动态的获取配置
//            String dynamicPatterns = "jfh.validation.pattern." + type;
//            String dynamicMessage = "jfh.validation.pattern." + type + ".message";
//            //对message进行key，value的替换
//            String message = ApolloMessageUtil.getString(dynamicMessage);
//            if(StringUtils.isNotBlank(message)){
//                message = message.replace("{key}",type).replace("{value}",s);
//                //这里需要将替换过的message重写到验证框架里面去
//                error(constraintValidatorContext,message);
//            }else {
//                //如果在代码里也配置了占位符，也会替换message
//                message = map.get("message").toString().replace("{key}",type).replace("{value}",s);
//                error(constraintValidatorContext,message);
//            }
//            String pattern = ApolloMessageUtil.getString(dynamicPatterns);
//            if(StringUtils.isNotBlank(pattern)){
//                return common(s,pattern);
//            }
//        }
//        return true;
//    }
//
//    private void error(ConstraintValidatorContext context, String message) {
//        context.disableDefaultConstraintViolation();
//        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
//    }
//
//
//    @Override
//    public void initialize(ValidateJfh constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
//    }
//}
