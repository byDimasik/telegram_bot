package ru.nsu.fit.telegrambot.model.enums;

import lombok.Getter;

public enum  CallBackEventType {
    ISSUE_CREATE("Create"),
    ISSUE_UPDATE("Update"),
    ISSUE_DELETE("Delete"),
    ISSUE_WORK_LOG("Worklog"),
    COMMENT_CREATE("Create"),
    COMMENT_UPDATE("Update"),
    COMMENT_DELETE("Delete"),
    SPRINT_CREATE("Create"),
    SPRINT_UPDATE("Update"),
    SPRINT_START("Start"),
    SPRINT_CLOSE("Close"),
    SPRINT_DELETE("Delete"),
    BACK("Back");

    @Getter
    private String buttonText;

    CallBackEventType(String buttonText) {
        this.buttonText = buttonText;
    }
}
