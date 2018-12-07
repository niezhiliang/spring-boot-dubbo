### spring-boot 集成 dubbo

#### **首先通过安装zookeeper，我这里使用的docker，有了docker感觉这个世界都简单了**

```jshelllanguage
//项目根目录下敲这行命令就好，docker 会自动去下zookeeper的镜像，
// 下完镜像就会将其启动起来
  docker-compose up -d 
```

- compon-api  公共api模块

- server-provider 服务提供模块

- server-customre 消费模块

#### **公共api模块**

这里我写的比较简单就是使用了一个hello方法

```java
    String hello(String message);
```
#### **服务提供模块**

maven依赖

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- dubbo -->
        <dependency>
            <groupId>com.alibaba.spring.boot</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>2.0.0</version>
        </dependency>
        <!-- zookeeper -->
        <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.4.8</version>
        </dependency>
        <dependency>
            <groupId>com.101tec</groupId>
            <artifactId>zkclient</artifactId>
            <version>0.10</version>
        </dependency>
        <!--公共api模块-->
        <dependency>
            <groupId>com.niezhiliang</groupId>
            <artifactId>common-api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```

启动类
```java
@SpringBootApplication
@EnableDubboConfiguration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
```

配置文件
```yml
server:
  port: 8080
spring:
  dubbo:
    application:
      # 服务名称，保持唯一
      name: server-provider
    # zookeeper地址，用于向其注册服务
    registry:
      address: zookeeper://127.0.0.1:2181
    #暴露服务方式
    protocol:
      # dubbo协议，固定写法
      name: dubbo
      # 暴露服务端口 （默认是20880，不同的服务提供者端口不能重复）
      port: 20880
    # 表示服务提供者，即服务暴露方
    server: true
    # 扫描需要暴露服务的类路径
    scan: com.niezhiliang.spring.boot.dubbo.server.provide.serviceImpl
```

暴露的方法
```java
//注意这个是dubbo的注解 不是spring的注解
@Service(interfaceClass = HelloService.class)
@Component
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String message) {
        return "hello,"+message;
    }
}
```


#### **服务消费模块**

maven依赖

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- dubbo -->
        <dependency>
            <groupId>com.alibaba.spring.boot</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>2.0.0</version>
        </dependency>
        <!-- zookeeper -->
        <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.4.8</version>
        </dependency>
        <dependency>
            <groupId>com.101tec</groupId>
            <artifactId>zkclient</artifactId>
            <version>0.10</version>
        </dependency>
        <!--公共api模块-->
        <dependency>
            <groupId>com.niezhiliang</groupId>
            <artifactId>common-api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```

配置文件
```yml
server:
  port: 8888

spring:
  dubbo:
    application:
      # 服务名称，保持唯一
      name: server-consumer
      # zookeeper地址，用于向其注册服务
    registry:
      address: zookeeper://127.0.0.1:2181
    protocol:
      # dubbo协议，固定写法
      name: dubbo
    # 扫描需要调用服务的类路径
    scan: com.niezhiliang.spring.boot.dubbo.server.customer.controller
```

启动类

```java
@SpringBootApplication
@EnableDubboConfiguration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
```

调用服务提供模块方法代码

```java
@RestController
public class IndexController {
    @Reference//dubbo内置注解
    private HelloService helloService;

    @RequestMapping(value = "hello")
    public String hello() {
        return helloService.hello("suyu");
    }
}

```

参考文章：https://mrbird.cc/Spring-Boot-Dubbo-Zookeeper.html

项目源码：https://github.com/niezhiliang/spring-boot-dubbo




