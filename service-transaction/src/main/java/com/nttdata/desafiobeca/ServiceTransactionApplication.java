package com.nttdata.desafiobeca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.nttdata.desafiobeca.infra.clientes")
public class ServiceTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceTransactionApplication.class, args);
    }

}
