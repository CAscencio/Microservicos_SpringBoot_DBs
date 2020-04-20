package com.microservicios.curso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.microservicios.curso.model",
        "com.commons.examenes.model"})
public class CursoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CursoApplication.class, args);
    }

}
