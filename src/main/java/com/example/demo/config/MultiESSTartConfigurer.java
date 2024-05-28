package com.example.demo.config;

import org.frameworkset.elasticsearch.boot.BBossESStarter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author xuming
 * @data 2023-01-06 11:01
 */
@Configuration
public class MultiESSTartConfigurer {


    @Primary
    @Bean(initMethod = "start")
    @ConfigurationProperties("spring.elasticsearch.bboss.icp-view")
    public BBossESStarter bBossESStarterDefault(){
        return new BBossESStarter();
    }
}
