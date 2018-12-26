package ru.nsu.fit.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.telegrambot.model.EventModel;
import ru.nsu.fit.telegrambot.model.enums.CallBackEventType;
import ru.nsu.fit.telegrambot.repository.EventRepository;

@Service
public class CallBackService {

    private final EventRepository eventRepository;

    @Autowired
    public CallBackService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void procesCallBackEvent(Long chatId, CallBackEventType eventType) {
        EventModel eventModel = eventRepository.findByChatId(chatId);

        switch (eventType) {
            case ISSUE_CREATE:
                eventModel.setIssueCreate(!eventModel.getIssueCreate());
                break;
            case ISSUE_UPDATE:
                eventModel.setIssueUpdate(!eventModel.getIssueUpdate());
                break;
            case ISSUE_DELETE:
                eventModel.setIssueDelete(!eventModel.getIssueDelete());
                break;
            case ISSUE_WORK_LOG:
                eventModel.setIssueWorkLog(!eventModel.getIssueWorkLog());
                break;
            case COMMENT_CREATE:
                eventModel.setCommentCreate(!eventModel.getCommentCreate());
                break;
            case COMMENT_DELETE:
                eventModel.setCommentDelete(!eventModel.getCommentDelete());
                break;
            case COMMENT_UPDATE:
                eventModel.setCommentUpdate(!eventModel.getCommentUpdate());
                break;
            case SPRINT_CREATE:
                eventModel.setSprintCreate(!eventModel.getSprintCreate());
                break;
            case SPRINT_CLOSE:
                eventModel.setSprintClose(!eventModel.getSprintClose());
                break;
            case SPRINT_DELETE:
                eventModel.setSprintDelete(!eventModel.getSprintDelete());
                break;
            case SPRINT_START:
                eventModel.setSprintStart(!eventModel.getSprintStart());
                break;
            case SPRINT_UPDATE:
                eventModel.setSprintUpdate(!eventModel.getSprintUpdate());
                break;
        }

        eventRepository.save(eventModel);
    }
}
