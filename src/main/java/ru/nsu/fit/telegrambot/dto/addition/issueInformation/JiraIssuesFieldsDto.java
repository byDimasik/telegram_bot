package ru.nsu.fit.telegrambot.dto.addition.issueInformation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssuesFieldsDto {
    private JiraIssueFieldsTypeDto issuetype;
    private String summary;
}
