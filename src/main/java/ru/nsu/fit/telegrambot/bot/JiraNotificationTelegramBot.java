package ru.nsu.fit.telegrambot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.nsu.fit.telegrambot.config.TelegramBotConfig;
import ru.nsu.fit.telegrambot.service.UserRegistrationService;

import java.util.List;

/**
 * Jira Notification Telegram Bot
 */
@Component
public class JiraNotificationTelegramBot extends TelegramLongPollingBot {

    private static final String TG_PREFIX = "@";
    private final TelegramBotConfig telegramBotConfig;
    private final UserRegistrationService userRegistrationService;

    /**
     * Constructor with spring dependency injection
     *
     * @param telegramBotConfig       {@link TelegramBotConfig} bean
     * @param userRegistrationService {@link UserRegistrationService} bean
     */
    @Autowired
    public JiraNotificationTelegramBot(TelegramBotConfig telegramBotConfig,
                                       UserRegistrationService userRegistrationService) {
        this.telegramBotConfig = telegramBotConfig;
        this.userRegistrationService = userRegistrationService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        userRegistrationService.register(update);
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        for (Update update : updates) {
            onUpdateReceived(update);
        }
    }

    @Override
    public String getBotUsername() {
        return TG_PREFIX + telegramBotConfig.getName();
    }

    @Override
    public String getBotToken() {
        return telegramBotConfig.getToken();
    }
}
