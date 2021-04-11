package com.soft.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * eureka服务端的启动类
 */
@SpringBootApplication
@EnableEurekaServer //开启eureka服务端配置
public class EurekaServer extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer.class,args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(EurekaServer.class);//项目启动类名
    }
}
