package com.baidu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @ClassName RunEurekaServerApplication
 * @Description: TODO
 * @Author wyj
 * @Date 2021/1/20
 * @Version V1.0
 **/
@SpringBootApplication
@EnableEurekaServer
public class RunEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RunEurekaServerApplication.class);
    }
}
