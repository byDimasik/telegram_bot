package ru.nsu.fit.telegrambot.viewModel;

import ru.nsu.fit.telegrambot.dto.JiraEventDto;
import ru.nsu.fit.telegrambot.dto.JiraIssueEventDto;

public class JiraEventFormatter {
    public String parseIssueEvent(JiraIssueEventDto event) {
        String message = event.getIssue().getFields().getCreator().getDisplayName();
        message += parseEventType(event);
        message += " " + event.getIssue().getFields().getIssuetype().getName().toLowerCase();
        message += " " + event.getIssue().getKey();
        message += " \"" + event.getIssue().getFields().getSummary() + "\"";
        return message;
    }

    private String parseEventType(JiraEventDto event) {
        String eventType = event.getWebhookEvent();
        String resultMessage = "";
        if (eventType.contains("created")) {
            resultMessage = " create";
        } else if (eventType.contains("updated")) {
            resultMessage = " update";
        } else if (eventType.contains("deleted")){
            resultMessage = " delete";
        }
        return resultMessage;
    }
}
