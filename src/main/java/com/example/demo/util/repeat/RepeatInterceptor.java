//package com.example.demo.util.repeat;
//
//import com.alibaba.fastjson.JSON;
//import com.example.demo.util.page.HttpHelper;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
///**
// * 请求路径一致，请求参数一致，请求的token一致，三个都相同则认为是重复提交数据
// * 重复提交拦截器
// * @author  liwenwu
// * @date  2021/9/24
// **/
//@Component
//public class RepeatInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    RedisTemplate redisTemplate;
//
//    /**
//     * 存入Redis的过期时间(秒)
//     */
//    @Value("${repeat.submission.expire}")
//    private Integer expire;
//
//    private static final String REPEATSUBMISSION = "REPEATSUBMISSION";
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        //获取请求头的token，token由前端传入，应该保证每次请求后端时都不一样
//        String token = request.getHeader("token");
//        //token不为空则校验，为空就不校验了
//        if(StringUtils.isNotBlank(token)){
//            //获取请求的路径
//            String uri = request.getRequestURI();
//            String method = request.getMethod();
//            String contentType = request.getContentType();
//            String param = "";
//            String body = "";
//            //从Redis获取对于的token结果，获取不到则直接放行
//            if(redisTemplate.hasKey(REPEATSUBMISSION + ":" + token)){
//                //post请求的application/json方式做过滤
//                if(StringUtils.equalsIgnoreCase("post",method) && StringUtils.equalsIgnoreCase("application/json",contentType)){
//                    //获取请求体数据
//                    body = HttpHelper.getBodyString(request.getInputStream());
//                    //redis里的请求数据
//                    Object object = redisTemplate.opsForValue().get(REPEATSUBMISSION + ":" + token);
//                    Map<String,String> map = JSON.parseObject(JSON.toJSONString(object),Map.class);
//                    if(token.equals(map.get("token")) && body.equals(map.get("body")) && uri.equals(map.get("uri"))){
//                        //三个都一致，说明重复提交了
//                        return false;
//                    }
//                    //这里没有对不是重复的数据进行覆盖
//                }else {
//                    //获取请求的参数
//                    Map<String, String[]> paramMap = request.getParameterMap();
//                    param = JSON.toJSONString(paramMap);
//                    //其他请求方式做过滤
//                    Object object = redisTemplate.opsForValue().get(REPEATSUBMISSION + ":" + token);
//                    Map<String,String> map = JSON.parseObject(JSON.toJSONString(object),Map.class);
//                    if(token.equals(map.get("token")) && param.equals(map.get("param")) && uri.equals(map.get("uri"))){
//                        //三个都一致，说明重复提交了
//                        return false;
//                    }
//                    //这里没有对不是重复的数据进行覆盖
//                }
//
//            }else {
//                //这里可以不区分body还是param
//                //否则将对应的数据保存Redis
//                if(StringUtils.equalsIgnoreCase("post",method) && StringUtils.equalsIgnoreCase("application/json",contentType)){
//                    Map<String,String> map = new HashMap<>();
//                    map.put("token",token);
//                    map.put("uri",uri);
//                    map.put("body",body);
//                    redisTemplate.opsForValue().set(REPEATSUBMISSION + ":" + token,map,expire, TimeUnit.SECONDS);
//
//                }else {
//                    Map<String,String> map = new HashMap<>();
//                    map.put("token",token);
//                    map.put("uri",uri);
//                    map.put("param",param);
//                    redisTemplate.opsForValue().set(REPEATSUBMISSION + ":" + token,map,expire, TimeUnit.SECONDS);
//                }
//
//            }
//
//        }
//        return true;
//    }
//
//
//}
