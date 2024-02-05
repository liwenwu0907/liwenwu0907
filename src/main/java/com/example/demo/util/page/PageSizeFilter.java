//package com.example.demo.util.page;
//
//import com.alibaba.fastjson.JSON;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.util.Map;
//
//@Component
//public class PageSizeFilter implements Filter {
//
//    @Value("${page-parameter.default.pageSize}")
//    private Integer defaultPageSize;
//
//    @Value("${page-parameter.max.pageSize}")
//    private Integer maxPageSize;
//
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        String method = request.getMethod();
//        String contentType = request.getContentType();
//        //post请求的application/json方式做过滤
//        if(StringUtils.equalsIgnoreCase("post",method) && StringUtils.equalsIgnoreCase("application/json",contentType)){
//            String body = HttpHelper.getBodyString(request.getInputStream());
//            Map map = JSON.parseObject(body, Map.class);
//            if(map.containsKey("pageSize")){
//                Integer result = checkPageSize(CustomUtil.formatEmptyValue(map.get("pageSize")));
//                map.put("pageSize",result);
//                body = JSON.toJSONString(map);
//            }
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
//            //重写输入流
//            BodyReaderHttpServletRequestWrapper bodyReaderHttpServletRequestWrapper = new BodyReaderHttpServletRequestWrapper(request,byteArrayInputStream);
//            filterChain.doFilter(bodyReaderHttpServletRequestWrapper,response);
//        }else if(request.getParameterMap().containsKey("pageSize")){
//            //其他请求方式做过滤
//            Integer result = checkPageSize(request.getParameter("pageSize"));
//            RequestParameterWrapper requestParameterWrapper = new RequestParameterWrapper(request);
//            requestParameterWrapper.addParameters("pageSize",result);
//            filterChain.doFilter(requestParameterWrapper,response);
//        }else {
//            filterChain.doFilter(servletRequest,servletResponse);
//        }
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
//
//    /**
//     * pageSize每页个数的校验
//     * @author  liwenwu
//     * @date  2021/9/24
//     **/
//    private Integer checkPageSize(String pageSize){
//        Integer needPageSize = null;
//        /*
//          判断请求参数中是否存在pageSize参数，如果有pageSize判断是否为空，如果为空赋默认值进去，
//          如果有值，判断是否大于指定值，如果大于指定值，就把pageSize赋值为指定值。
//         */
//        if(StringUtils.isBlank(pageSize)){
//            needPageSize = defaultPageSize;
//        }else {
//            needPageSize = Integer.parseInt(pageSize);
//        }
//        if(StringUtils.isNotBlank(pageSize) && Integer.parseInt(pageSize)>maxPageSize){
//            needPageSize = maxPageSize;
//        }
//        if(needPageSize <= 0){
//            needPageSize = defaultPageSize;
//        }
//        return needPageSize;
//    }
//}
