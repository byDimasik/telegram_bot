package ru.nsu.fit.telegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nsu.fit.telegrambot.bot.JiraNotificationTelegramBot;
import ru.nsu.fit.telegrambot.dto.JiraEventDto;
import ru.nsu.fit.telegrambot.dto.JiraIssueEventDto;
import ru.nsu.fit.telegrambot.dto.JiraSprintEventDto;
import ru.nsu.fit.telegrambot.repository.EventRepository;
import ru.nsu.fit.telegrambot.viewModel.JiraEventFormatter;

/**
 * Event service
 */
@Slf4j
@Service
public class EventService {

    private final JiraNotificationTelegramBot bot;
    private final EventRepository eventRepository;
    private final JiraEventFormatter eventFormatter = new JiraEventFormatter();

    /**
     * Constructor with spring dependency injection
     *
     * @param bot             {@link JiraNotificationTelegramBot} bean
     * @param eventRepository {@link EventRepository} bean
     */
    @Autowired
    public EventService(JiraNotificationTelegramBot bot,
                        EventRepository eventRepository) {
        this.bot = bot;
        this.eventRepository = eventRepository;
    }

    /**
     * Handle common event
     *
     * @param jiraEventDto common jira event
     */
    public void handleEvent(JiraEventDto jiraEventDto) {
        eventRepository.findAll().forEach(event -> {
            SendMessage sendMessage = new SendMessage()
                    .setChatId(event.getChatId())
                    .setText(jiraEventDto.getWebhookEvent());

            try {
                bot.execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error("Cant execute SendMessage", e);
            }
        });
    }

    private void handleMessage(String eventText) {
        eventRepository.findAll().forEach(event -> {
            SendMessage sendMessage = new SendMessage()
                    .setChatId(event.getChatId())
                    .setText(eventText);

            try {
                bot.execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error("Cant execute SendMessage", e);
            }
        });
    }

    public void handleIssueEvent(JiraIssueEventDto event) {
//        handleMessage(eventFormatter.parseIssueEvent(event));
        System.out.println(eventFormatter.parseIssueEvent(event));
    }

    public void handleSprintEvent(JiraSprintEventDto event) {
//        handleMessage(eventFormatter.parseIssueEvent(event));
//        System.out.println(eventFormatter.parseIssueEvent(event));
    }
}
