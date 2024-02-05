package com.example.demo.util.msdatasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author li wenwu
 * @date 2020/12/22 11:52
 */
@Aspect
@Component
public class DataSourceAOP {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceAOP.class);

    @Pointcut("execution(* com.example.demo.service.*.*(..))")
    private void doInterface() {
    }

    //横切点
    @Before("doInterface()")
    public void process(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        if (methodName.startsWith("get") || methodName.startsWith("count") || methodName.startsWith("find")
                || methodName.startsWith("list") || methodName.startsWith("select") || methodName.startsWith("check")
        || methodName.startsWith("query")) {
            DynamicDataSourceHolder.putDataSource(DataSourceType.READ);
            logger.info("使用的是读数据源：readDataSource");
        } else {
            DynamicDataSourceHolder.putDataSource(DataSourceType.WRITE);
            logger.info("使用的是写数据源：writeDataSource");
        }
    }
}
