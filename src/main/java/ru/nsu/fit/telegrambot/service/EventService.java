package ru.nsu.fit.telegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nsu.fit.telegrambot.bot.JiraNotificationTelegramBot;
import ru.nsu.fit.telegrambot.dto.JiraCommentEventDto;
import ru.nsu.fit.telegrambot.dto.JiraFeatureEventDto;
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
     * Handle common fromatted event message
     *
     * @param eventText formatted jira event text
     */
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
        handleMessage(eventFormatter.parseIssueEvent(event));
//        System.out.println(eventFormatter.parseIssueEvent(event));
    }

    public void handleSprintEvent(JiraSprintEventDto event) {
        handleMessage(eventFormatter.parseSprintEvent(event));
//        System.out.println(eventFormatter.parseSprintEvent(event));
    }

    public void handleCommentaryEvent(JiraCommentEventDto event) {
        handleMessage(eventFormatter.parseCommentaryEvent(event));
//        System.out.println(eventFormatter.parseCommentaryEvent(event));
    }

    public void handleFeatureEvent(JiraFeatureEventDto event) {
        handleMessage(eventFormatter.parseFeatureEvent(event));
//        System.out.println(eventFormatter.parseIssueEvent(event));
    }
}
