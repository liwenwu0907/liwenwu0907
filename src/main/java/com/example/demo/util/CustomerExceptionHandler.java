//package com.example.demo.util;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestControllerAdvice
//@Slf4j
//public class CustomerExceptionHandler extends ResponseEntityExceptionHandler {
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
//        StringBuffer errorMsg=new StringBuffer();
//        errors.stream().forEach(x -> errorMsg.append(x.getDefaultMessage()).append(";"));
//        logger.error( "参数校验异常1:" + errors);
//        return super.handleExceptionInternal(ex, Result.builder().code(String.valueOf(status.value())).msg(errorMsg.toString()).build(), headers, status, request);
//    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Result constraintViolationException(ConstraintViolationException constraintViolationException) {
//        String errorCode = "400";
//        String errorMsg = constraintViolationException.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));
//
//        log.warn("{} , code={}, msg={}", "参数异常", errorCode, errorMsg);
//        return Result.builder().code(errorCode).msg(errorMsg).build();
//    }
//
////    @Override
////    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
////        String errorMsg = "";
////        if (ex instanceof MethodArgumentNotValidException) {
////            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
////            errorMsg = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(";"));
////        }
////        logger.error( "参数校验异常2:" + errorMsg);
////        return super.handleExceptionInternal(ex, Result.builder().code(String.valueOf(status.value())).msg(errorMsg).build(), headers, status, request);
////    }
//}
