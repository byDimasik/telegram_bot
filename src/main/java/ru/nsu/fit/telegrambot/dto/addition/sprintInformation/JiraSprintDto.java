package ru.nsu.fit.telegrambot.dto.addition.sprintInformation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraSprintDto {
    private String name;
}
