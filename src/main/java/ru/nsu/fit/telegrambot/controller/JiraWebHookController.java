package ru.nsu.fit.telegrambot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class JiraWebHookController {
    @PostMapping(path = "/sprint")
    public void springHook(@RequestBody String event) {
        System.out.println(event);
    }

    @PostMapping(path = "/commentary")
    public void commentaryHook(@RequestBody String event) {
        System.out.println(event);
    }

    @PostMapping(path = "/feature")
    public void featureHook(@RequestBody String event) {
        System.out.println(event);
    }

    @PostMapping(path = "/issue")
    public void issueHook(@RequestBody String event) {
        System.out.println(event);
    }
}
