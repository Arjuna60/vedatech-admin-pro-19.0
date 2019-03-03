package com.vedatech.pro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class VedaTechAdminProApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(VedaTechAdminProApplication.class, args);
    }


    @Override
    protected SpringApplicationBuilder configure (SpringApplicationBuilder builder) {
        return builder.sources(VedaTechAdminProApplication.class);
    }

}

