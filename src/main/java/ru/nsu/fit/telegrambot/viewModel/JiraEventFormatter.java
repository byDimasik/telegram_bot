package ru.nsu.fit.telegrambot.viewModel;

import ru.nsu.fit.telegrambot.dto.*;
import ru.nsu.fit.telegrambot.dto.addition.issueInformation.JiraIssueDto;
import ru.nsu.fit.telegrambot.model.enums.CallBackEventType;

public class JiraEventFormatter {
    public String parseIssueEvent(JiraIssueEventDto event) {
        String message = event.getIssue().getFields().getCreator().getDisplayName();
        message += " " + parseEventType(event);
        message += " " + partIssueMessage(event.getIssue()) + ".";
        return message;
    }

    public String parseFeatureEvent(JiraFeatureEventDto event) {
        return partIssueMessage(event.getIssue());
    }

    public String parseSprintEvent(JiraSprintEventDto event) {
        String message = "Sprint";
        message += " \"" + event.getSprint().getName() + "\"";
        message += " " + parseEventType(event);
        message += ". State - \"" + event.getSprint().getState() + "\"";
        if(event.getSprint().getGoal() != null) {
            message += ". Goal - \"" + event.getSprint().getGoal() +"\".";
        }
        return message;
    }

    public JiraEventTypeWithMessage parseCommentaryEvent(JiraCommentEventDto event) {
        JiraEventTypeWithMessage result = new JiraEventTypeWithMessage();
        String message = event.getComment().getAuthor().getDisplayName();
        String messageType = parseEventType(event);
        message += " " + messageType;
        message += " " + partIssueMessage(event.getIssue());
        message += ". Commentary text \"" + event.getComment().getBody() + "\".";
        switch (messageType) {
            case "create":
                result.setType(CallBackEventType.COMMENT_CREATE);
                break;
            case "update":
                result.setType(CallBackEventType.COMMENT_UPDATE);
                break;
            case "delete":
                result.setType(CallBackEventType.COMMENT_DELETE);
                break;
            default:
                break;
        }
        result.setMessage(message);
        return result;
    }

    private String partIssueMessage(JiraIssueDto issue) {
        JiraEventTypeWithMessage result;
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
            resultMessage = "start";
        } else if (eventType.contains("closed")) {
            resultMessage = "close";
        }
        return resultMessage;
    }
}
