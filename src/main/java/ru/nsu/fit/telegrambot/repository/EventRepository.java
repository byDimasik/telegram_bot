package ru.nsu.fit.telegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.telegrambot.model.EventModel;

import java.util.List;

/**
 * Repository to manage with {@link EventModel} entity
 */
@Repository
public interface EventRepository extends CrudRepository<EventModel, Long> {

    /**
     * Find {@link EventModel} by primary key - chat id
     *
     * @param chatId chat id
     * @return event model
     */
    EventModel findByChatId(Long chatId);

    /**
     * Find all chat ids to send issue create event
     *
     * @param issueCreate issue create flag
     * @return list of chat ids
     */
    List<EventModel> findAllChatIdByIssueCreateIs(Boolean issueCreate);

    /**
     * Find all chat ids to send issue update event
     *
     * @param issueUpdate issue update flag
     * @return list of chat ids
     */
    List<EventModel> findAllChatIdByIssueUpdateIs(Boolean issueUpdate);

    /**
     * Find all chat ids to send issue delete event
     *
     * @param issueDelete issue delete flag
     * @return list of chat ids
     */
    List<EventModel> findAllChatIdByIssueDeleteIs(Boolean issueDelete);

    /**
     * Find all chat ids to send issue work log event
     *
     * @param issueWorkLog issue work log flag
     * @return list of chat ids
     */
    List<EventModel> findAllChatIdByIssueWorkLogIs(Boolean issueWorkLog);

    /**
     * Find all chat ids to send comment create event
     *
     * @param commentCreate comment create flag
     * @return list of chat ids
     */
    List<EventModel> findAllChatIdByCommentCreateIs(Boolean commentCreate);

    /**
     * Find all chat ids to send comment update event
     *
     * @param commentUpdate comment update flag
     * @return list of chat ids
     */
    List<EventModel> findAllChatIdByCommentUpdateIs(Boolean commentUpdate);

    /**
     * Find all chat ids to send comment delete event
     *
     * @param commentDelete comment delete flag
     * @return list of chat ids
     */
    List<EventModel> findAllChatIdByCommentDeleteIs(Boolean commentDelete);

    /**
     * Find all chat ids to send sprint create event
     *
     * @param sprintCreate sprint create flag
     * @return list of chat ids
     */
    List<EventModel> findAllChatIdBySprintCreateIs(Boolean sprintCreate);

    /**
     * Find all chat ids to send sprint update event
     *
     * @param sprintUpdate sprint update flag
     * @return list of chat ids
     */
    List<EventModel> findAllChatIdBySprintUpdateIs(Boolean sprintUpdate);

    /**
     * Find all chat ids to send sprint delete event
     *
     * @param sprintDelete sprint delete flag
     * @return list of chat ids
     */
    List<EventModel> findAllChatIdBySprintDeleteIs(Boolean sprintDelete);

    /**
     * Find all chat ids to send sprint start event
     *
     * @param sprintStart sprint start flag
     * @return list of chat ids
     */
    List<EventModel> findAllChatIdBySprintStartIs(Boolean sprintStart);

    /**
     * Find all chat ids to send sprint close event
     *
     * @param sprintClose sprint close flag
     * @return list of chat ids
     */
    List<EventModel> findAllChatIdBySprintCloseIs(Boolean sprintClose);
}
