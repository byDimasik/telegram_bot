package ru.nsu.fit.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Event model
 */
@Data
@Entity
@Builder
@Table(name = "event")
@NoArgsConstructor
@AllArgsConstructor
public class EventModel implements Serializable {

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

    @Column(name = "comment_create")
    private Boolean commentCreate;

    @Column(name = "comment_update")
    private Boolean commentUpdate;

    @Column(name = "comment_delete")
    private Boolean commentDelete;

    @Column(name = "sprint_create")
    private Boolean sprintCreate;

    @Column(name = "sprint_update")
    private Boolean sprintUpdate;

    @Column(name = "sprint_delete")
    private Boolean sprintDelete;

    @Column(name = "sprint_start")
    private Boolean sprintStart;

    @Column(name = "sprint_close")
    private Boolean sprintClose;

}
