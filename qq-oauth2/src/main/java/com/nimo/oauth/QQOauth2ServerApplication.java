package com.nimo.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class QQOauth2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(QQOauth2ServerApplication.class, args);
    }

}
