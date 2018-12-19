package ru.nsu.fit.telegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.telegrambot.model.EventModel;

@Repository
public interface EventRepository extends CrudRepository<EventModel, Long> {

}
