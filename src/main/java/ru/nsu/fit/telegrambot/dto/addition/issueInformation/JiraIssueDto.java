package ru.nsu.fit.telegrambot.dto.addition.issueInformation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssueDto {
    private JiraIssueFieldsDto fields;
    private String key;
}
