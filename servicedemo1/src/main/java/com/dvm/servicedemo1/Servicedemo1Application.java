package com.dvm.servicedemo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Servicedemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(Servicedemo1Application.class, args);
    }

}
