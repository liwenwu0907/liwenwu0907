//package com.example.demo.util.repeat;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class RepeatConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration registration = registry.addInterceptor(getInterceptor());
//        //所有路径都被拦截
//        registration.addPathPatterns("/**");
//        //添加不拦截路径
//        registration.excludePathPatterns("/**/*.html", "/**/*.js", "/**/*.css", "/**/*.woff", "/**/*.ttf");
//
//
//    }
//
//    @Bean
//    public RepeatInterceptor getInterceptor(){
//        return new RepeatInterceptor();
//    }
//
//}
