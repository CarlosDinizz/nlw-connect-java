package com.nlw.events.services;

import com.nlw.events.models.Event;
import com.nlw.events.repositories.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private EventRepo repo;

    @Autowired
    public EventService(EventRepo repo) {
        this.repo = repo;
    }

    public Event addNewEvent(Event event){
        event.setPrettyName(event.getTitle().toLowerCase().replaceAll(" ", "-"));
        return repo.save(event);
    }

    public List<Event> getAllEvents(){
        return (List<Event>)repo.findAll();
    }

    public Event getByPrettyName(String prettyName){
        return repo.findByPrettyName(prettyName);
    }
}
