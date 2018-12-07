package com.niezhiliang.spring.boot.dubbo.server.provide.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.niehziliang.common.api.service.HelloService;
import org.springframework.stereotype.Component;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2018/12/7 下午3:25
 */
@Service(interfaceClass = HelloService.class)
@Component
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String message) {
        return "hello,"+message;
    }
}
