package com.xciting.gamelauncher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${game.code.length}")
    private int gameCodeLength;

    @Bean
    public int gameCodeLength() {
        return gameCodeLength;
    }
}
