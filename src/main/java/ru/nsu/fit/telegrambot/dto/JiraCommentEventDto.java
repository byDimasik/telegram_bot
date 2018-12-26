package ru.nsu.fit.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.nsu.fit.telegrambot.dto.addition.commentInformation.JiraCommentDto;
import ru.nsu.fit.telegrambot.dto.addition.issueInformation.JiraIssueDto;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraCommentEventDto extends JiraEventDto {
    private JiraCommentDto comment;
    private JiraIssueDto issue;
}
