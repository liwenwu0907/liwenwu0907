package com.example.demo;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class,scanBasePackages = {"com.example.demo.*"})
@MapperScan("com.example.demo.dao")
@Configuration
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        //initFlowRules();
        SpringApplication.run(DemoApplication.class, args);

    }

    public static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("hello2222");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(1);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    @Override
    public void run(String... args) throws Exception {
        String imagePath = "C:\\Users\\lenovo\\Desktop\\faceDetector\\java\\1.jpg";
        String outFile = "C:\\Users\\lenovo\\Desktop\\faceDetector\\java\\2.jpg";
//        try {
//            detectEye(imagePath, outFile);
//            //detectFace(imagePath,outFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
