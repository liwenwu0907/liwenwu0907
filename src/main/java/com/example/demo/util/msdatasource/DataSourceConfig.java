package com.example.demo.util.msdatasource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author li wenwu
 * @date 2020/12/22 11:46
 */
@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.type}")
    private Class<? extends DataSource> dataSourceType;


    @Bean(name = "readDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.read")
    public DataSource readDataSource() {
        DataSource readDataSource = DataSourceBuilder.create().type(dataSourceType).build();
        return readDataSource;
    }

    @Bean(name = "writeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.write")
    public DataSource writeDataSource() {
        DataSource writeDataSource = DataSourceBuilder.create().type(dataSourceType).build();
        return writeDataSource;
    }

    //动态数据源
    @Bean(name = "dynamicDataSource")
    //解决互相依赖关系
    @DependsOn({ "writeDataSource", "readDataSource"})
    @Primary
    public DataSource getDataSource() {
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources());
        return dataSource;
    }

    private Map<Object, Object> targetDataSources() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.WRITE.getType(), writeDataSource());
        targetDataSources.put(DataSourceType.READ.getType(), readDataSource());
        return targetDataSources;
    }

}
