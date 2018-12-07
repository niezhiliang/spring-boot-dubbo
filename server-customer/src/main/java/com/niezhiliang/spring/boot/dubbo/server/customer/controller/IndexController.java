package com.niezhiliang.spring.boot.dubbo.server.customer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.niehziliang.common.api.service.HelloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2018/12/7 下午3:23
 */
@RestController
public class IndexController {
    @Reference
    private HelloService helloService;

    @RequestMapping(value = "hello")
    public String hello() {
        return helloService.hello("suyu");
    }
}
