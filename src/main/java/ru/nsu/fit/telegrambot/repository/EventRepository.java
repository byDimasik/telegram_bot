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
     * Find all chat ids to send issue create event
     *
     * @param issueCreate issue create flag
     * @return list of chat ids
     */
    List<Long> findAllChatIdByIssueCreateIs(Boolean issueCreate);

    /**
     * Find all chat ids to send issue update event
     *
     * @param issueUpdate issue update flag
     * @return list of chat ids
     */
    List<Long> findAllChatIdByIssueUpdateIs(Boolean issueUpdate);

    /**
     * Find all chat ids to send issue delete event
     *
     * @param issueDelete issue delete flag
     * @return list of chat ids
     */
    List<Long> findAllChatIdByIssueDeleteIs(Boolean issueDelete);

    /**
     * Find all chat ids to send issue work log event
     *
     * @param issueWorkLog issue work log flag
     * @return list of chat ids
     */
    List<Long> findAllChatIdByIssueWorkLogIs(Boolean issueWorkLog);

    /**
     * Find all chat ids to send comment create event
     *
     * @param commentCreate comment create flag
     * @return list of chat ids
     */
    List<Long> findAllChatIdByCommentCreateIs(Boolean commentCreate);

    /**
     * Find all chat ids to send comment update event
     *
     * @param commentUpdate comment update flag
     * @return list of chat ids
     */
    List<Long> findAllChatIdByCommentUpdateIs(Boolean commentUpdate);

    /**
     * Find all chat ids to send comment delete event
     *
     * @param commentDelete comment delete flag
     * @return list of chat ids
     */
    List<Long> findAllChatIdByCommentDeleteIs(Boolean commentDelete);
}
