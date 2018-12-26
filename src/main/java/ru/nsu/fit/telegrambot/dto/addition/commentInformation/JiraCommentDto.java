package ru.nsu.fit.telegrambot.dto.addition.commentInformation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.nsu.fit.telegrambot.dto.addition.sharedInformation.JiraUserDto;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraCommentDto {
    private String body;
    private JiraUserDto author;
}
