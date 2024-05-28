package com.example.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@Order(1)
public class TableExistAspect {

    @Pointcut("@annotation(com.example.demo.annotion.TableExist)")
    public void dynamicTable() {
    }

    @Around("dynamicTable()")
    public Object dynamicTable(ProceedingJoinPoint point) throws Throwable {
        try {
            return point.proceed();
        } catch (BadSqlGrammarException e) {
            //简单处理下。Unknown column是缺失表字段，doesn't exist是表不存在
            if (e.getMessage().contains("doesn't exist")) {
                log.error(e.getMessage());
            } else {
                throw e;
            }
        }
        return null;
    }
}
