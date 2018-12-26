package ru.nsu.fit.telegrambot.dto.addition.commentInformation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraCommentDto {
    private String body;
}
