package ru.nsu.fit.telegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nsu.fit.telegrambot.bot.JiraNotificationTelegramBot;
import ru.nsu.fit.telegrambot.repository.EventRepository;

@Slf4j
@Service
public class EventService {

    private final JiraNotificationTelegramBot bot;
    private final EventRepository eventRepository;

    @Autowired
    public EventService(JiraNotificationTelegramBot bot,
                        EventRepository eventRepository) {
        this.bot = bot;
        this.eventRepository = eventRepository;
    }

    public void handleEvent() {
        eventRepository.findAll().forEach(event -> {
            SendMessage sendMessage = new SendMessage()
                    .setChatId(event.getChatId())
                    .setText("Smth happened");

            try {
                bot.execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error("Cant execute SendMessage", e);
            }
        });
    }
}
