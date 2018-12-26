package ru.nsu.fit.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.nsu.fit.telegrambot.dto.addition.issueInformation.JiraIssueDto;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraFeatureEventDto extends JiraEventDto {
    private JiraIssueDto issue;
}
