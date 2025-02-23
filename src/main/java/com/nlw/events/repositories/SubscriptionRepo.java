package com.nlw.events.repositories;

import com.nlw.events.models.Event;
import com.nlw.events.models.Subscription;
import com.nlw.events.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepo extends CrudRepository<Subscription, Integer> {
    Subscription findByEventAndSubscriber(Event event, User subscriber);
}
