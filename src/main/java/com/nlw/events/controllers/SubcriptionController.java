package com.nlw.events.controllers;

import com.nlw.events.dto.ErrorMessage;
import com.nlw.events.dto.SubscriptionResponse;
import com.nlw.events.exceptions.EventNotFoundException;
import com.nlw.events.exceptions.SubscriptionConflictException;
import com.nlw.events.exceptions.UserIndicatorNotFoundException;
import com.nlw.events.models.Event;
import com.nlw.events.models.Subscription;
import com.nlw.events.models.User;
import com.nlw.events.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubcriptionController {

    private SubscriptionService service;

    @Autowired
    public SubcriptionController(SubscriptionService service) {
        this.service = service;
    }

    @PostMapping({"subscription/{prettyName}", "subscription/{prettyName}/{userId}"})
    public ResponseEntity<?> createNewSubscription(
            @PathVariable String prettyName,
            @RequestBody User subscriber,
            @PathVariable Integer userId){
        try {

            SubscriptionResponse subscription = service.createNewSubscription(prettyName, subscriber, userId);
            if (subscription != null) {
                return ResponseEntity.ok(subscription);
            }

        }
        catch (EventNotFoundException | UserIndicatorNotFoundException e){
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
        }
        catch (SubscriptionConflictException e){
            return ResponseEntity.status(409).body(new ErrorMessage(e.getMessage()));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("subscription/{prettyName}/ranking")
    public ResponseEntity<?> getCompleteRanking(@PathVariable String prettyName){
        try{
            return ResponseEntity.ok(service.getCompleteRanking(prettyName));
        }
        catch (EventNotFoundException e){
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
        }
    }

    @GetMapping("subscription/{prettyName}/ranking/{userId}")
    public ResponseEntity<?> getRankingByUser(@PathVariable String prettyName, @PathVariable Integer userId){
        try {
            return ResponseEntity.ok(service.getRankingByUser(prettyName, userId));
        }
        catch (UserIndicatorNotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
