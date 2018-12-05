package ru.nsu.fit.telegrambot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JiraWebHookController {

    @PostMapping(path = "/hook")
    public void webHook(@RequestBody String event) {
        System.out.println(event);
    }
}
