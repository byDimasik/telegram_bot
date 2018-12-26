package ru.nsu.fit.telegrambot.utill;

import lombok.Data;
import ru.nsu.fit.telegrambot.model.enums.CallBackEventType;

@Data
public class JiraEventTypeWithMessage {
    private String message;
    private CallBackEventType type;
}
