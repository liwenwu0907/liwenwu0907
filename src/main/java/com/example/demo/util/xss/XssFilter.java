//package com.example.demo.util.xss;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import org.apache.commons.lang3.ArrayUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Enumeration;
//import java.util.Map;
//
//@Component
//@WebFilter(urlPatterns = {"/*"},filterName = "xssFilter")
//public class XssFilter implements Filter {
//
//    /**
//     * 不需要拦截的URL
//     */
//    @Value("${xss.url.excludes:}")
//    private String excludes;
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        //可以对特殊的url进行处理
//        String url = request.getRequestURI();
//        String method = request.getMethod();
//        if(StringUtils.isNotBlank(excludes)){
//            String[] urlExcludes = excludes.split(",");
//            if(ArrayUtils.contains(urlExcludes,url)){
//                filterChain.doFilter(request,response);
//            }
//        }
//        //对header进行校验
//        Enumeration<String> allHeader = request.getHeaderNames();
//        while (allHeader.hasMoreElements()){
//            String key = allHeader.nextElement();
//            String value = request.getHeader(key);
//            String compare = XssShieldUtil.stripXss(value);
//            if(!StringUtils.equals(compare, value)){
//                throw new RuntimeException("非法字符");
//            }
//        }
//        //对cookie进行校验
//        Cookie[] allCookie = request.getCookies();
//        if(null != allCookie && allCookie.length>0){
//            for(Cookie cookie:allCookie){
//                String value = cookie.getValue();
//                String compare = XssShieldUtil.stripXss(value);
//                if(!StringUtils.equals(compare, value)){
//                    throw new RuntimeException("非法字符");
//                }
//            }
//        }
//        if(StringUtils.equalsIgnoreCase("post", method)){
//            String contentType = request.getContentType();
//            //这里只对JSON和x-www-form-urlencoded做校验
//            if(contentType.contains("application/json")){
//                ServletRequest req = new BodyReaderHttpServletRequestWrapper(request);
//                String body = HttpHelper.getBodyString(req);
//                System.out.println(url + "的json请求参数：" + body);
//                if(body.startsWith("{")){
//                    JSONObject jsonObject = JSON.parseObject(body);
//                    if(IteratorAllParam(jsonObject)){
//                        //获取body之后，不能再使用之前的request
//                        filterChain.doFilter(req,response);
//                    }else {
//                        throw new RuntimeException("非法字符");
//                    }
//                }else if(body.startsWith("[")){
//                    JSONArray jsonArray = JSON.parseArray(body);
//                    if(IteratorAllParam(jsonArray)){
//                        //获取body之后，不能再使用之前的request
//                        filterChain.doFilter(req,response);
//                    }else {
//                        throw new RuntimeException("非法字符");
//                    }
//                }
//
//            }else if(contentType.contains("application/x-www-form-urlencoded")){
//                Map<String,String[]> requestMap = request.getParameterMap();
//                System.out.println(url + "的post请求参数：" + JSON.toJSONString(requestMap));
//                requestMap.forEach((k,v)->{
//                    String compare = XssShieldUtil.stripXss(v[0]);
//                    if(!StringUtils.equals(compare, v[0])){
//                        throw new RuntimeException("非法字符");
//                    }
//                });
//            }
//        }else if(StringUtils.equalsIgnoreCase("get", method)){
//            //对普通get请求的URL参数进行校验
//            Map<String,String[]> requestMap = request.getParameterMap();
//            System.out.println(url + "的get请求参数：" + JSON.toJSONString(requestMap));
//            requestMap.forEach((k,v)->{
//                String compare = XssShieldUtil.stripXss(v[0]);
//                if(!StringUtils.equals(compare, v[0])){
//                    throw new RuntimeException("非法字符");
//                }
//            });
//        }
//        filterChain.doFilter(request,response);
//    }
//
//    /**
//     * 遍历所有参数，有非法字符则返回FALSE
//     * @author  liwenwu
//     * @date  2022/6/17
//     **/
//    public boolean IteratorAllParam(Object object) {
//        if (object != null) {
//            if (object instanceof JSONObject) {
//                for (String str : ((JSONObject) object).keySet()) {
//                    return IteratorAllParam(((JSONObject) object).get(str));
//                }
//            } else if (object instanceof JSONArray) {
//                if (((JSONArray) object).size() > 0) {
//                    for (int i = 0; i < ((JSONArray) object).size(); i++) {
//                        Object arrayObject = ((JSONArray) object).get(i);
//                        return IteratorAllParam(arrayObject);
//                    }
//                }
//            } else {
//                //直接处理数据
//                String compare = XssShieldUtil.stripXss(object.toString());
//                return StringUtils.equals(compare, object.toString());
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }
//
//    @Override
//    public void destroy() {
//        Filter.super.destroy();
//    }
//}
