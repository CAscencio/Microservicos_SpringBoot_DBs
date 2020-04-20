package com.microservicio.respuestas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.microservicio.respuestas.model",
        "com.commons.examenes.model"})
public class RespuestasApplication {

    public static void main(String[] args) {
        SpringApplication.run(RespuestasApplication.class, args);
    }

}
