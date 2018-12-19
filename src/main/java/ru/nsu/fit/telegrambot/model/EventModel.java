package ru.nsu.fit.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "event")
@NoArgsConstructor
@AllArgsConstructor
public class EventModel implements Serializable {

    private static final Boolean COLUMN_DEFAULT = true;

    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "issue_create")
    private Boolean issueCreate;

    @Column(name = "issue_update")
    private Boolean issueUpdate;

    @Column(name = "issue_delete")
    private Boolean issueDelete;

    @Column(name = "issue_worklog")
    private Boolean issueWorkLog;

    public EventModel(Long chatId) {
        this(chatId, COLUMN_DEFAULT, COLUMN_DEFAULT, COLUMN_DEFAULT, COLUMN_DEFAULT);
    }
}
