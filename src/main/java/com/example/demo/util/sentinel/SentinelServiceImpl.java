package com.example.demo.util.sentinel;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

@Service
public class SentinelServiceImpl implements SentinelService{


    @SentinelResource(value = "hello2222", blockHandler = "exceptionHandler",fallback = "helloFallback")
    @Override
    public String hello2222(long s){
        return String.format("Hello at %d", s);
    }


    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public String helloFallback(long s) {
        return String.format("Halooooo %d", s);
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String exceptionHandler(long s, BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return "Oops, error occurred at " + s;
    }


    @SentinelResource(blockHandler = "sayHelloBlockHandler")
    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }

    public String sayHelloBlockHandler(String name, BlockException ex) {
        // This is the block handler.
        ex.printStackTrace();
        return String.format("Oops, <%s> blocked by Sentinel", name);
    }
}
