//package com.example.demo.util.validation;
//
//import javax.validation.Constraint;
//import javax.validation.Payload;
//import java.lang.annotation.*;
//
//@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@Constraint(
//        validatedBy = {ValidateJfhValidation.class}
//)
//public @interface ValidateJfh {
//
//    String type();
//
//    String message();
//
//    Class<?>[] groups() default {};
//
//    Class<? extends Payload>[] payload() default {};
//
//}
