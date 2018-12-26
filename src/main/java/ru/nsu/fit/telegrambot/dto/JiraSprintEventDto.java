package ru.nsu.fit.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.nsu.fit.telegrambot.dto.addition.sprintInformation.JiraSprintDto;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraSprintEventDto extends JiraEventDto {
    private JiraSprintDto sprint;
}
