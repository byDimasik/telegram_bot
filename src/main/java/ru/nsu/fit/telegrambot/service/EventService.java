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
import ru.nsu.fit.telegrambot.model.EventModel;
import ru.nsu.fit.telegrambot.repository.EventRepository;
import ru.nsu.fit.telegrambot.utill.JiraEventFormatter;
import ru.nsu.fit.telegrambot.utill.JiraEventTypeWithMessage;

import java.util.ArrayList;
import java.util.List;

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
    private void handleTypedMessage(List<EventModel> chats, String eventText) {
        chats.forEach(event -> {
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
        JiraEventTypeWithMessage typedMessage = eventFormatter.parseIssueEvent(event);
        List<EventModel> resultList = new ArrayList<>();
        switch (typedMessage.getType()) {
            case ISSUE_CREATE:
                resultList = eventRepository.findAllChatIdByIssueCreateIs(true);
                break;
            case ISSUE_DELETE:
                resultList = eventRepository.findAllChatIdByIssueDeleteIs(true);
                break;
            case ISSUE_UPDATE:
                resultList = eventRepository.findAllChatIdByIssueUpdateIs(true);
                break;
            default:
                break;
        }
        if (resultList.size() != 0) {
            handleTypedMessage(resultList, typedMessage.getMessage());
        }
    }

    public void handleSprintEvent(JiraSprintEventDto event) {
        JiraEventTypeWithMessage typedMessage = eventFormatter.parseSprintEvent(event);
        List<EventModel> resultList = new ArrayList<>();
        switch (typedMessage.getType()) {
            case SPRINT_CREATE:
                resultList = eventRepository.findAllChatIdBySprintCreateIs(true);
                break;
            case SPRINT_DELETE:
                resultList = eventRepository.findAllChatIdBySprintDeleteIs(true);
                break;
            case SPRINT_START:
                resultList = eventRepository.findAllChatIdBySprintStartIs(true);
                break;
            case SPRINT_UPDATE:
                resultList = eventRepository.findAllChatIdBySprintUpdateIs(true);
                break;
            case SPRINT_CLOSE:
                resultList = eventRepository.findAllChatIdBySprintCloseIs(true);
                break;
            default:
                break;
        }
        if (resultList.size() != 0) {
            handleTypedMessage(resultList, typedMessage.getMessage());
        }
    }

    public void handleCommentaryEvent(JiraCommentEventDto event) {
        JiraEventTypeWithMessage typedMessage = eventFormatter.parseCommentaryEvent(event);
        List<EventModel> resultList = new ArrayList<>();
        switch (typedMessage.getType()) {
            case COMMENT_CREATE:
                resultList = eventRepository.findAllChatIdByCommentCreateIs(true);
                break;
            case COMMENT_DELETE:
                resultList = eventRepository.findAllChatIdByCommentDeleteIs(true);
                break;
            case COMMENT_UPDATE:
                resultList = eventRepository.findAllChatIdByCommentUpdateIs(true);
                break;
            default:
                break;
        }
        if (resultList.size() != 0) {
            handleTypedMessage(resultList, typedMessage.getMessage());
        }
    }

    public void handleFeatureEvent(JiraFeatureEventDto event) {
        JiraEventTypeWithMessage typedMessage = eventFormatter.parseFeatureEvent(event);
        List<EventModel> resultList = eventRepository.findAllChatIdByIssueWorkLogIs(true);
        if (resultList.size() != 0) {
            handleTypedMessage(resultList, typedMessage.getMessage());
        }
    }
}
