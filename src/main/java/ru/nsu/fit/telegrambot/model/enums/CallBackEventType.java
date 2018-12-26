package ru.nsu.fit.telegrambot.model.enums;

import lombok.Getter;

public enum  CallBackEventType {
    ISSUE_CREATE("Create", MenuType.ISSUE),
    ISSUE_UPDATE("Update", MenuType.ISSUE),
    ISSUE_DELETE("Delete", MenuType.ISSUE),
    ISSUE_WORK_LOG("Worklog", MenuType.ISSUE),
    COMMENT_CREATE("Create", MenuType.COMMENT),
    COMMENT_UPDATE("Update", MenuType.COMMENT),
    COMMENT_DELETE("Delete", MenuType.COMMENT),
    SPRINT_CREATE("Create", MenuType.SPRINT),
    SPRINT_UPDATE("Update", MenuType.SPRINT),
    SPRINT_START("Start", MenuType.SPRINT),
    SPRINT_CLOSE("Close", MenuType.SPRINT),
    SPRINT_DELETE("Delete", MenuType.SPRINT),
    BACK("Back", MenuType.BACK);

    @Getter
    private String buttonText;

    @Getter
    private MenuType menuType;

    CallBackEventType(String buttonText, MenuType menuType) {
        this.buttonText = buttonText;
        this.menuType = menuType;
    }
}
