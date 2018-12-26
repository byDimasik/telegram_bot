package ru.nsu.fit.telegrambot.dto.addition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssuesFieldsDto {
    private JiraIssueFieldsTypeDto issuetype;
}
