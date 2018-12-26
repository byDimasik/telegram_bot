package ru.nsu.fit.telegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.nsu.fit.telegrambot.model.EventModel;
import ru.nsu.fit.telegrambot.repository.EventRepository;

import java.util.Optional;

/**
 * User registration service
 */
@Slf4j
@Service
public class UserRegistrationService {

    private static final boolean DEFAULT_VALUE = false;

    private final EventRepository eventRepository;

    /**
     * Constructor with spring dependency injection
     *
     * @param eventRepository {@link EventRepository} bean
     */
    @Autowired
    public UserRegistrationService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Register user (only if new)
     *
     * @param update telegram update
     */
    public void register(Update update) {
        if (Optional.ofNullable(update).map(Update::getMessage).map(Message::getChatId).isPresent()) {
            Long chatId = update.getMessage().getChatId();

            if (!eventRepository.findById(chatId).isPresent()) {
                log.debug("Received update from unregister user with chat id: [{}]", chatId);
                EventModel eventModel = EventModel.builder()
                        .chatId(chatId)
                        .issueCreate(DEFAULT_VALUE)
                        .issueDelete(DEFAULT_VALUE)
                        .issueUpdate(DEFAULT_VALUE)
                        .issueWorkLog(DEFAULT_VALUE)
                        .commentCreate(DEFAULT_VALUE)
                        .commentUpdate(DEFAULT_VALUE)
                        .commentDelete(DEFAULT_VALUE)
                        .sprintClose(DEFAULT_VALUE)
                        .sprintCreate(DEFAULT_VALUE)
                        .sprintDelete(DEFAULT_VALUE)
                        .sprintStart(DEFAULT_VALUE)
                        .sprintUpdate(DEFAULT_VALUE)
                        .build();
                eventRepository.save(eventModel);
                log.debug("User has been successfully registered with chat id: [{}]", chatId);
            }
        } else {
            log.trace("Received bad update: [{}]", update);
        }
    }
}
