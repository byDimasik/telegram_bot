package ru.nsu.fit.telegrambot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.telegrambot.dto.JiraCommentEventDto;
import ru.nsu.fit.telegrambot.dto.JiraFeatureEventDto;
import ru.nsu.fit.telegrambot.dto.JiraIssueEventDto;
import ru.nsu.fit.telegrambot.dto.JiraSprintEventDto;
import ru.nsu.fit.telegrambot.service.EventService;

/**
 * Rest controller for events from jira
 */
@Slf4j
@RestController
public class JiraWebHookController {

    private final EventService eventService;

    @Autowired
    public JiraWebHookController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping(path = "/sprint")
    public void springHook(@RequestBody JiraSprintEventDto event) {
        log.debug("/sprint invoked");
//        eventService.handleEvent(event);
        eventService.handleSprintEvent(event);
    }

    @PostMapping(path = "/commentary")
    public void commentaryHook(@RequestBody JiraCommentEventDto event) {
        log.debug("/commentary invoked");
        eventService.handleCommentaryEvent(event);
    }

    @PostMapping(path = "/feature")
    public void featureHook(@RequestBody JiraFeatureEventDto event) {
        log.debug("/feature invoked");
//        eventService.handleEvent(event);
    }

    @PostMapping(path = "/issue")
    public void issueHook(@RequestBody JiraIssueEventDto event) {
        log.debug("/issue invoked");
//        eventService.handleEvent(event);
        eventService.handleIssueEvent(event);
    }
}
