package com.marcosfausto.helpdesk.config;

import com.marcosfausto.helpdesk.services.DBService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class DevConfig {

    private final DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String value;

    @Bean
    public boolean instanciaDB() {
        if (value.equals("create")) {
            this.dbService.instaciaDB();
        }
        return false;
    }
}
