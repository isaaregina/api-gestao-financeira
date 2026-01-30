package com.nttdata.desafiobeca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ServiceProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProcessorApplication.class, args);
    }

}
