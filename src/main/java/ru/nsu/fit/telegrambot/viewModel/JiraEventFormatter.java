package ru.nsu.fit.telegrambot.viewModel;

import ru.nsu.fit.telegrambot.dto.JiraCommentEventDto;
import ru.nsu.fit.telegrambot.dto.JiraEventDto;
import ru.nsu.fit.telegrambot.dto.JiraIssueEventDto;
import ru.nsu.fit.telegrambot.dto.JiraSprintEventDto;
import ru.nsu.fit.telegrambot.dto.addition.issueInformation.JiraIssueDto;

public class JiraEventFormatter {
    public String parseIssueEvent(JiraIssueEventDto event) {
        String message = event.getIssue().getFields().getCreator().getDisplayName();
        message += " " + parseEventType(event);
        message += " " + partIssueMessage(event.getIssue());
        return message;
    }

    public String parseSprintEvent(JiraSprintEventDto event) {
        String message = "Sprint";
        message += " \"" + event.getSprint().getName() + "\"";
        message += " " + parseEventType(event);
        message += ". State - \"" + event.getSprint().getState() + "\"";
        if(event.getSprint().getGoal() != null) {
            message += ". Goal - \"" + event.getSprint().getGoal() +"\"";
        }
        return message;
    }

    public String parseCommentaryEvent(JiraCommentEventDto event) {
        String message = event.getComment().getAuthor().getDisplayName();
        message += " commented on " + partIssueMessage(event.getIssue());
        message += ". Commentary text \"" + event.getComment().getBody() + "\"";
        return message;
    }

    private String partIssueMessage(JiraIssueDto issue) {
        String message = issue.getFields().getIssuetype().getName().toLowerCase();
        message += " " + issue.getKey();
        message += " \"" + issue.getFields().getSummary() + "\"";
        return message;
    }

    private String parseEventType(JiraEventDto event) {
        String eventType = event.getWebhookEvent();
        String resultMessage = "";
        if (eventType.contains("created")) {
            resultMessage = "create";
        } else if (eventType.contains("updated")) {
            resultMessage = "update";
        } else if (eventType.contains("deleted")){
            resultMessage = "delete";
        } else if (eventType.contains("started")) {
            resultMessage = "started";
        } else if (eventType.contains("closed")) {
            resultMessage = "closed";
        }
        return resultMessage;
    }
}
