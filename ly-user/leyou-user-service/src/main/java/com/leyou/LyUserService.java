package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @description: 用户中心启动器
 * @author: furong
 * @date: 2019/5/7 15:11
 * @Version: 1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.leyou.user.mapper")
public class LyUserService {
    public static void main(String[] args) {
        SpringApplication.run(LyUserService.class);
    }
}
