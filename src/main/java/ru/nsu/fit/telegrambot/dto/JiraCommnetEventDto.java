package ru.nsu.fit.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraCommnetEventDto extends JiraEventDto {
}
