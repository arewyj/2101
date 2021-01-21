package com.baidu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @ClassName RunZuulServerApplication
 * @Description: TODO
 * @Author wyj
 * @Date 2021/1/20
 * @Version V1.0
 **/
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class RunZuulServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunZuulServerApplication.class);
    }
}
