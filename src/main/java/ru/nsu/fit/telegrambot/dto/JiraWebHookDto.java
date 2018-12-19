package ru.nsu.fit.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraWebHookDto {

    private String name;

    private String url;

    private List<String> events;
}
