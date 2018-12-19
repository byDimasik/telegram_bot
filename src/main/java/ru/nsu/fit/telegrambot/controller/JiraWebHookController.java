package ru.nsu.fit.telegrambot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.telegrambot.dto.JiraSprintEventDto;
import ru.nsu.fit.telegrambot.service.EventService;


@RestController
public class JiraWebHookController {

    private final EventService eventService;

    @Autowired
    public JiraWebHookController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping(path = "/hook")
    public void webHook(@RequestBody JiraSprintEventDto event) {
        System.out.println(event);
        eventService.handleEvent();
    }
}
