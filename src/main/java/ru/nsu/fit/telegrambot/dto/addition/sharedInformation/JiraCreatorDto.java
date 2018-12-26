package ru.nsu.fit.telegrambot.dto.addition.sharedInformation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraCreatorDto {
    private String name;
    private String displayName;
}
