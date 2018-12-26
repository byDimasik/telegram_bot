package ru.nsu.fit.telegrambot.viewModel;

import ru.nsu.fit.telegrambot.dto.*;
import ru.nsu.fit.telegrambot.dto.addition.issueInformation.JiraIssueDto;
import ru.nsu.fit.telegrambot.model.enums.CallBackEventType;

public class JiraEventFormatter {
    public JiraEventTypeWithMessage parseIssueEvent(JiraIssueEventDto event) {
        JiraEventTypeWithMessage result = new JiraEventTypeWithMessage();
        String messageType = parseEventType(event);
        String message = event.getIssue().getFields().getCreator().getDisplayName();
        message += " " + messageType;
        message += " " + partIssueMessage(event.getIssue()) + ".";
        switch (messageType) {
            case "create":
                result.setType(CallBackEventType.ISSUE_CREATE);
                break;
            case "update":
                result.setType(CallBackEventType.ISSUE_UPDATE);
                break;
            case "delete":
                result.setType(CallBackEventType.ISSUE_DELETE);
                break;
            default:
                break;
        }
        result.setMessage(message);
        return result;
    }

    public JiraEventTypeWithMessage parseFeatureEvent(JiraFeatureEventDto event) {
        JiraIssueEventDto tempDto = new JiraIssueEventDto();
        tempDto.setIssue(event.getIssue());
        return parseIssueEvent(tempDto);
    }

    public JiraEventTypeWithMessage parseSprintEvent(JiraSprintEventDto event) {
        JiraEventTypeWithMessage result = new JiraEventTypeWithMessage();
        String messageType = parseEventType(event);
        String message = "Sprint";
        message += " \"" + event.getSprint().getName() + "\"";
        message += " " + messageType;
        message += ". State - \"" + event.getSprint().getState() + "\"";
        if(event.getSprint().getGoal() != null) {
            message += ". Goal - \"" + event.getSprint().getGoal() +"\".";
        }
        switch (messageType) {
            case "create":
                result.setType(CallBackEventType.SPRINT_CREATE);
                break;
            case "update":
                result.setType(CallBackEventType.SPRINT_UPDATE);
                break;
            case "delete":
                result.setType(CallBackEventType.SPRINT_DELETE);
                break;
            case "start":
                result.setType(CallBackEventType.SPRINT_START);
            case "close":
                result.setType(CallBackEventType.SPRINT_CLOSE);
            default:
                break;
        }
        result.setMessage(message);
        return result;
    }

    public JiraEventTypeWithMessage parseCommentaryEvent(JiraCommentEventDto event) {
        JiraEventTypeWithMessage result = new JiraEventTypeWithMessage();
        String messageType = parseEventType(event);
        String message = event.getComment().getAuthor().getDisplayName();
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
