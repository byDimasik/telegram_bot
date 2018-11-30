package ru.nsu.fit.telegrambot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nsu.fit.telegrambot.config.TelegramBotConfig;

import java.util.List;

@Component
public class JiraNotificationTelegramBot extends TelegramLongPollingBot {

    private static final String TG_PREFIX = "@";
    private final TelegramBotConfig telegramBotConfig;

    @Autowired
    public JiraNotificationTelegramBot(TelegramBotConfig telegramBotConfig) {
        this.telegramBotConfig = telegramBotConfig;
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText(update.getMessage().getText());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        for (Update update: updates) {
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
