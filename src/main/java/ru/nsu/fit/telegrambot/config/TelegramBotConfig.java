package ru.nsu.fit.telegrambot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "telegram-bot")
public class TelegramBotConfig {

    private String token;
}
