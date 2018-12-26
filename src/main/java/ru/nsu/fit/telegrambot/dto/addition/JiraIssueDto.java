package ru.nsu.fit.telegrambot.dto.addition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssueDto {
    private JiraIssuesFieldsDto fields;
    private String key;
}
