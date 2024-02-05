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
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Map;
//
//@Component
//@WebFilter(urlPatterns = {"/*"}, filterName = "sqlFilter")
//public class SqlFilter implements Filter {
//
//    /**
//     * 不需要拦截的URL
//     */
//    @Value("${sql.url.excludes:}")
//    private String excludes;
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        //可以对特殊的url进行处理
//        String url = request.getRequestURI();
//        String method = request.getMethod();
//        if (StringUtils.isNotBlank(excludes)) {
//            String[] urlExcludes = excludes.split(",");
//            if (ArrayUtils.contains(urlExcludes, url)) {
//                filterChain.doFilter(request, response);
//            }
//        }
//        if (StringUtils.equalsIgnoreCase("post", method)) {
//            String contentType = request.getContentType();
//            //这里只对JSON和x-www-form-urlencoded做校验
//            if (contentType.contains("application/json")) {
//                ServletRequest req = new BodyReaderHttpServletRequestWrapper(request);
//                String body = HttpHelper.getBodyString(req);
//                System.out.println(url + "的json请求参数：" + body);
//                if (body.startsWith("{")) {
//                    JSONObject jsonObject = JSON.parseObject(body);
//                    if (IteratorAllParam(jsonObject)) {
//                        //获取body之后，不能再使用之前的request
//                        filterChain.doFilter(req, response);
//                    } else {
//                        throw new RuntimeException("sql注入");
//                    }
//                } else if (body.startsWith("[")) {
//                    JSONArray jsonArray = JSON.parseArray(body);
//                    if (IteratorAllParam(jsonArray)) {
//                        //获取body之后，不能再使用之前的request
//                        filterChain.doFilter(req, response);
//                    } else {
//                        throw new RuntimeException("sql注入");
//                    }
//                }
//
//            } else if (contentType.contains("application/x-www-form-urlencoded")) {
//                Map<String, String[]> requestMap = request.getParameterMap();
//                System.out.println(url + "的post请求参数：" + JSON.toJSONString(requestMap));
//                requestMap.forEach((k, v) -> {
//                    if(sqlValidate(v[0])){
//                        throw new RuntimeException("sql注入");
//                    }
//                });
//            }
//        } else if (StringUtils.equalsIgnoreCase("get", method)) {
//            //对普通get请求的URL参数进行校验
//            Map<String, String[]> requestMap = request.getParameterMap();
//            System.out.println(url + "的get请求参数：" + JSON.toJSONString(requestMap));
//            requestMap.forEach((k, v) -> {
//                if(sqlValidate(v[0])){
//                    throw new RuntimeException("sql注入");
//                }
//            });
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    protected static boolean sqlValidate(String str) {
//        //统一转为小写
//        str = str.toLowerCase();
//        //过滤掉的sql关键字，可以手动添加
//        String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|chr|mid|master|truncate|char|declare|sitename|net user|xp_cmdshell|or|like";
//        String[] badStrs = badStr.split("\\|");
//        for (String s : badStrs) {
//            if (str.contains(s)) {
//                System.out.println("sql注入匹配到：" + s);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 遍历所有参数，有非法字符则返回FALSE
//     *
//     * @author liwenwu
//     * @date 2022/6/17
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
//                return !sqlValidate(object.toString());
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
