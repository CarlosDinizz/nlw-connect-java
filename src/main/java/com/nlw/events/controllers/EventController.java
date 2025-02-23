package com.nlw.events.controllers;

import com.nlw.events.models.Event;
import com.nlw.events.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {

    private EventService service;

    @Autowired
    public EventController(EventService service) {
        this.service = service;
    }

    @PostMapping("/events")
    public Event addNewEvent(@RequestBody Event event){
        return service.addNewEvent(event);
    }

    @GetMapping("/events")
    public List<Event> getAllEvents(){
        return service.getAllEvents();
    }

    @GetMapping("/events/{prettyName}")
    public ResponseEntity<Event> getEventByPrettyName(@PathVariable String prettyName){
        Event event = service.getByPrettyName(prettyName);

        if (event != null){
            return ResponseEntity.ok(event);
        }
        
        return  ResponseEntity.notFound().build();
    }
}
