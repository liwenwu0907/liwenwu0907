package com.example.demo.util.ratelimiter;

import com.example.demo.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author liwenwu
 * @date 2022/7/8
 **/
@Aspect
@Component
@Slf4j
public class RedisRLimiterAspect {

    private static final String REDIS_LIMIT_KEY_HEAD = "limit";

    @Autowired
    private RedissonClient redisson;

    // 切入点
    @Pointcut("@annotation(com.example.demo.util.ratelimiter.RedisRLimiter)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        RedisRLimiter limit = method.getAnnotation(RedisRLimiter.class);
        String ip = IpUtils.getIpAddr(request);
        String key;
        switch (limit.limitType()) {
            case IP:
                // ip类型
                key = ip;
                break;
            case CUSTOMER:
                // 传统类型，采用注解提供key
                key = limit.key();
                break;
            default:
                // 默认采用方法名
                key = StringUtils.upperCase(method.getName());
        }
//        ImmutableList keys = ImmutableList.of(StringUtils.join(REDIS_LIMIT_KEY_HEAD, limit.prefix(), ":", ip, key));
        // 生成key
        final String ofRateLimiter = REDIS_LIMIT_KEY_HEAD + ip + key;
        RRateLimiter rateLimiter = redisson.getRateLimiter(ofRateLimiter);

        // 根据官方文档的描述
        /*
         * RRateLimiter rateLimiter = redisson.getRateLimiter("myRateLimiter");
         * // 初始化
         * // 最大流速 = 每1秒钟产生10个令牌
         * rateLimiter.trySetRate(RateType.OVERALL, 10, 1, RateIntervalUnit.SECONDS);
         * // 需要1个令牌
           if(rateLimiter.tryAcquire(1)){
                // TODO:Do something
           }
         */

        // 设置访问速率，var2为访问数，var4为单位时间，var6为时间单位
        // 每10秒产生1个令牌 总体限流
        // 创建令牌桶数据模型
        rateLimiter.trySetRate(limit.mode(), limit.count(), limit.period(), RateIntervalUnit.SECONDS);

        // permits 允许获得的许可数量 (如果获取失败，返回false) 1秒内不能获取到1个令牌，则返回，不阻塞
        // 尝试访问数据，占数据计算值var1，设置等待时间var3
        // acquire() 默认如下参数 如果超时时间为-1，则永不超时，则将线程阻塞，直至令牌补充
        // 此处采用3秒超时方式，服务降级
        if (!rateLimiter.tryAcquire()) {
            log.error("IP【{}】访问接口【{}】超出频率限制，限制规则为[限流模式：{}; 限流数量：{}; 限流时间间隔：{};]",
                    ip, method.getName(), limit.mode().toString(), limit.count(), limit.period());
            throw new RuntimeException("接口访问超出频率限制，请稍后重试");
        }
        return point.proceed();
    }

}
