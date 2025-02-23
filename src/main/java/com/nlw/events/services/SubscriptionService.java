package com.nlw.events.services;

import com.nlw.events.dto.SubscriptionResponse;
import com.nlw.events.exceptions.EventNotFoundException;
import com.nlw.events.exceptions.SubscriptionConflictException;
import com.nlw.events.exceptions.UserIndicatorNotFoundException;
import com.nlw.events.models.Event;
import com.nlw.events.models.Subscription;
import com.nlw.events.models.User;
import com.nlw.events.repositories.EventRepo;
import com.nlw.events.repositories.SubscriptionRepo;
import com.nlw.events.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    private SubscriptionRepo repo;
    private EventRepo eventRepo;
    private UserRepo userRepo;

    @Autowired
    public SubscriptionService(SubscriptionRepo repo, EventRepo eventRepo, UserRepo userRepo) {
        this.repo = repo;
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
    }

    public SubscriptionResponse createNewSubscription(String prettyName, User user, Integer userId){
        Event event = eventRepo.findByPrettyName(prettyName);
        if (event == null){
            throw new EventNotFoundException("O evento " + prettyName + " não existe");
        }
        User userRec = userRepo.findByEmail(user.getEmail());
        if (userRec == null){
            userRec = userRepo.save(user);
        }

        Subscription subscription = new Subscription();

        User indicator = userRepo.findById(userId).orElse(null);
        if (indicator == null){
            throw new UserIndicatorNotFoundException("O usuário " + userId + " indicador não existe");
        }

        subscription.setEvent(event);
        subscription.setSubscriber(userRec);
        subscription.setIndication(indicator);

        Subscription temp = repo.findByEventAndSubscriber(event, userRec);

        if (temp != null){
            throw new SubscriptionConflictException("Já existe inscrição para o usuário " + userRec.getName() + " para o evento " + event.getTitle());
        }

        Subscription sub = repo.save(subscription);

        return new SubscriptionResponse(sub.getSubscriptionNumber(), "http://codecraft.com/" + sub.getEvent().getPrettyName() + "/" + sub.getSubscriber().getId());
    }
}
