//package com.example.demo.util.validation;
//
//import com.jfh.common.exception.ServiceException;
//import com.jfh.validation.constant.BusinessConstant;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//import java.util.regex.Pattern;
//
///**
// * JFH注解验证逻辑实现
// * @author  liwenwu
// * @date  2021/10/26
// **/
//@Component
//@Aspect
//public class JFHAspect {
//
//    /**
//     * jfid正则配置
//     */
//    @Value("${jfh.validation.pattern.jfid}")
//    private String patternJfid;
//
//    /**
//     * buid正则配置
//     */
//    @Value("${jfh.validation.pattern.buid}")
//    private String patternBuid;
//
//    /**
//     * 手机号正则配置
//     */
//    @Value("${jfh.validation.pattern.phone}")
//    private String patternPhone;
//
//    /**
//     * email正则配置
//     */
//    @Value("${jfh.validation.pattern.email}")
//    private String patternEmail;
//
//    @Pointcut("execution(* com.jfh..*(..)) && (@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController) || @within(com.jfh.validation.annotation.JFH))")
//    public void access() {
//
//    }
//
//    @Before("access()")
//    public void doBefore(JoinPoint joinPoint){
//        //首先使用注解默认值不为null，可以为空字符串
//        if(null == joinPoint.getArgs() || null == joinPoint.getArgs()[0]){
//            throw new ServiceException(BusinessConstant.JFHERRORCODE,"参数不可以为null");
//        }
//        String value = joinPoint.getArgs()[0].toString();
//        Signature signature = joinPoint.getSignature();
//        MethodSignature methodSignature = (MethodSignature) signature;
//        Method targetMethod = methodSignature.getMethod();
//        JFH jfh = targetMethod.getAnnotation(JFH.class);
//        String type = jfh.type();
//        String message = jfh.message();
//        switch (type){
//            case BusinessConstant.ISJFID:
//                checkJfid(value,message);
//                break;
//            case BusinessConstant.ISBUID:
//                checkBuid(value,message);
//                break;
//            case BusinessConstant.ISPHONE:
//                checkPhone(value,message);
//                break;
//            case BusinessConstant.ISEMAIL:
//                checkEmail(value,message);
//                break;
//            default:
//                checkUndefind(value,message);
//                break;
//        }
//
//    }
//
//    /**
//     * jfid验证方法
//     * @author  liwenwu
//     * @date  2021/10/26
//     **/
//    private void checkJfid(String value,String message){
//        if(StringUtils.isBlank(value)){
//            throw new ServiceException(BusinessConstant.JFHERRORCODE,"jfid不可为空");
//        }
//        if(StringUtils.isBlank(message)){
//            message = "jfid不符合规范";
//        }
//        Pattern pattern = Pattern.compile(patternJfid);
//        if(!pattern.matcher(value).matches()){
//            throw new ServiceException(BusinessConstant.JFHERRORCODE,message);
//        }
//    }
//
//    /**
//     * buid验证方法
//     * @author  liwenwu
//     * @date  2021/10/26
//     **/
//    private void checkBuid(String value,String message){
//        if(StringUtils.isBlank(value)){
//            throw new ServiceException(BusinessConstant.JFHERRORCODE,"buid不可为空");
//        }
//        if(StringUtils.isBlank(message)){
//            message = "buid不符合规范";
//        }
//        Pattern pattern = Pattern.compile(patternBuid);
//        if(!pattern.matcher(value).matches()){
//            throw new ServiceException(BusinessConstant.JFHERRORCODE,message);
//        }
//    }
//
//    /**
//     * 手机号验证方法
//     * @author  liwenwu
//     * @date  2021/10/26
//     **/
//    private void checkPhone(String value,String message){
//        if(StringUtils.isBlank(value)){
//            throw new ServiceException(BusinessConstant.JFHERRORCODE,"手机号不可为空");
//        }
//        if(StringUtils.isBlank(message)){
//            message = "手机号不符合规范";
//        }
//        Pattern pattern = Pattern.compile(patternPhone);
//        if(!pattern.matcher(value).matches()){
//            throw new ServiceException(BusinessConstant.JFHERRORCODE,message);
//        }
//    }
//
//    /**
//     * email验证方法
//     * @author  liwenwu
//     * @date  2021/10/26
//     **/
//    private void checkEmail(String value,String message){
//        if(StringUtils.isBlank(value)){
//            throw new ServiceException(BusinessConstant.JFHERRORCODE,"email不可为空");
//        }
//        if(StringUtils.isBlank(message)){
//            message = "email不符合规范";
//        }
//        Pattern pattern = Pattern.compile(patternEmail);
//        if(!pattern.matcher(value).matches()){
//            throw new ServiceException(BusinessConstant.JFHERRORCODE,message);
//        }
//    }
//
//    /**
//     * 暂不支持的验证方法
//     * @author  liwenwu
//     * @date  2021/10/26
//     **/
//    private void checkUndefind(String value,String message){
//        throw new ServiceException(BusinessConstant.JFHERRORCODE,"暂不支持其他验证类型");
//    }
//}
