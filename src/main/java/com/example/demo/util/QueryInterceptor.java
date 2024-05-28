package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.example.demo.annotion.TableExist;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 查询拦截器，用于查询前判断表是否存在，防止报错
 * @Author: liwenwu
 * @Date: 2024/4/7
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
@Slf4j
@Component
public class QueryInterceptor implements Interceptor {

    @Override
    public void setProperties(Properties properties) {

    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //自动装配不生效
//        Dc3TableExistsMapper dc3TableExistsMapper = SpringUtils.getBean("dc3TableExistsMapper");
//        TableExistsMapper tableExistsMapper = SpringUtils.getBean("tableExistsMapper");
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        // 先判断是不是SELECT操作 不是直接过滤
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }

        final Object[] args = invocation.getArgs();
        //获取执行方法的位置
        String namespace = mappedStatement.getId();
        //获取mapper名称
        String className = namespace.substring(0, namespace.lastIndexOf("."));
        //获取方法名
        String methedName = namespace.substring(namespace.lastIndexOf(".") + 1, namespace.length());
        //获取当前mapper 的方法
        Method[] ms = Class.forName(className).getMethods();
        for (Method m : ms) {
            if (m.getName().equals(methedName)) {
                //获取注解  来判断是不是要判断表
                TableExist annotation = m.getAnnotation(TableExist.class);
                if (annotation != null && annotation.value()) {
                    BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
                    // 原来的SQL语句
                    String originalSql = boundSql.getSql();
                    log.info("originalSql:" + originalSql);
                    //获取SQL的tableName
                    List<String> tableList = extract(originalSql);
                    log.info("tableList:" + JSON.toJSONString(tableList));
                    //获取类的DS注解，用于判断哪个库。暂时用这种笨方法
                    Class<?> mapperClass = Class.forName(className);
//                    DS dsAnnotation = mapperClass.getAnnotation(DS.class);
//                    if (dsAnnotation != null && "slave".equals(dsAnnotation.value())) {
//                        //slave库查
//                        for (String table : tableList) {
//                            if (null == dc3TableExistsMapper.dc3ShowTableExists(table)) {
//                                log.error(table + "表不存在");
//                                //由于直接重写空值SQL未搞定，暂时在原SQL里拼接where 1=2
////                                String nullCollection = "select null from dual";
//                                originalSql = giveNullCollectionSql(originalSql,table);
//                                PluginUtils.MPBoundSql mpBoundSql = PluginUtils.mpBoundSql(boundSql);
//                                mpBoundSql.sql(originalSql);
//                                return invocation.proceed();
//                            }
//                        }
//                    } else {
//                        //master库查
//                        for (String table : tableList) {
////                            if (null == tableExistsMapper.showTableExists(table)) {
////                                log.error(table + "表不存在");
////                                originalSql = giveNullCollectionSql(originalSql,table);
////                                PluginUtils.MPBoundSql mpBoundSql = PluginUtils.mpBoundSql(boundSql);
////                                mpBoundSql.sql(originalSql);
////                            }
//                        }
//                    }
                }
            }
        }
        // 如果没有自定义注解，正常执行
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    private List<String> extract(String sql) {
        List<String> tableNames = new ArrayList<>();
        // 正则表达式匹配表名的模式
        // 匹配方式：`table_name`、[table_name]、"table_name"、table_name（空格或者标点符号分隔）
        String regex = "(?i)\\bfrom\\s+(?<table>[^\\s]+)";
//        String regex = "(?i)\\bfrom\\s+([\\w`\"\\[\\]]+)|(?<=\\s)(?i)\\b(inner\\s+join\\s+|left\\s+join\\s+|right\\s+join\\s+|join\\s+|,\\s+)([\\w`\"\\[\\]]+)(?=\\s)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sql);

        while (matcher.find()) {
            String tableName = matcher.group(1);
            if (tableName == null) {
                tableName = matcher.group(3);
            }
            if (tableName != null) {
                tableNames.add(tableName.trim().replace("`", ""));
            }
        }
        return tableNames;
    }


    /**
     * @Description: 在原SQL的基础上新增where 1=2
     * @Author: liwenwu
     * @Date: 2024/4/8
     */
    private String giveNullCollectionSql(String originalSql, String table){
        String[] whereArr = originalSql.split("where|WHERE");
        StringBuilder stringBuilder = new StringBuilder();
        if(whereArr.length > 1){
            //拼接 1=2
            for(int i = 0; i < whereArr.length; i++){
                stringBuilder.append(whereArr[i]);
                if(i != whereArr.length - 1){
                    stringBuilder.append(" where 1=2 and ");
                }
            }
        }else {
            //拼接where 1=2
            String[] tableArr = originalSql.split(table);
            for(int i = 0; i < tableArr.length; i++){
                stringBuilder.append(tableArr[i]);
                if(i != tableArr.length - 1){
                    stringBuilder.append(table).append(" where 1=2 ");
                }
            }
        }
        originalSql = stringBuilder.toString();
        log.info("修改完的SQL：" + originalSql);
        return originalSql;
    }
}
