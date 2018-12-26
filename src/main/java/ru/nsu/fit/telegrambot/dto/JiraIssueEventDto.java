package ru.nsu.fit.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.nsu.fit.telegrambot.dto.addition.JiraIssueDto;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssueEventDto extends JiraEventDto {
    private JiraIssueDto issue;
}
