//package com.example.demo.util.msdatasource;
//
//import org.apache.ibatis.mapping.DatabaseIdProvider;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.aspectj.apache.bcel.util.ClassLoaderRepository;
//import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
//import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
//import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//import org.springframework.stereotype.Component;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//import java.util.List;
//
///**
// * @author li wenwu
// * @date 2020/12/22 11:49
// */
//@Component
//public class MybatisConfiguration extends MybatisAutoConfiguration {
//
//    public MybatisConfiguration(MybatisProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider, ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider, ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
//        super(properties, interceptorsProvider, resourceLoader, databaseIdProvider, configurationCustomizersProvider);
//    }
//
//    private static final Logger logger = LoggerFactory.getLogger(MybatisConfiguration.class);
//
//    @Resource(name = "readDataSource")
//    private DataSource readDataSource;
//
//    @Resource(name = "writeDataSource")
//    private DataSource writeDataSource;
//
//
//    @Bean(name="sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        //放入datasource 需要mybatis的AbstractRoutingDataSource 实现主从切换
//        return super.sqlSessionFactory(roundRobinDataSourceProxy());
//    }
//
//    public AbstractRoutingDataSource roundRobinDataSourceProxy(){
//
//        DynamicDataSource proxy = new DynamicDataSource();
//        //proxy.
//        ClassLoaderRepository.SoftHashMap targetDataSource = new ClassLoaderRepository.SoftHashMap();
//        targetDataSource.put(DataSourceType.WRITE, writeDataSource);
//        targetDataSource.put(DataSourceType.READ, readDataSource);
//        //默认数据源
//        proxy.setDefaultTargetDataSource(writeDataSource);
//        //装入两个主从数据源
//        proxy.setTargetDataSources(targetDataSource);
//        return proxy;
//    }
//@Bean
//@ConditionalOnMissingBean //有此实例便不进行注册
//public QueryInterceptor dataScopeInterceptor() {
//        return new QueryInterceptor();
//        }
//
//
//
//
//}
