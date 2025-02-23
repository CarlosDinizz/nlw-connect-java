package com.nlw.events.repositories;

import com.nlw.events.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepo extends CrudRepository<Event, Integer> {
    Event findByPrettyName(String prettyName);
}
