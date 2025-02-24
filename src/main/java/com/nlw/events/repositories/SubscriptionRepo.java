package com.nlw.events.repositories;

import com.nlw.events.dto.SubscriptionRankingItem;
import com.nlw.events.models.Event;
import com.nlw.events.models.Subscription;
import com.nlw.events.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepo extends CrudRepository<Subscription, Integer> {
    Subscription findByEventAndSubscriber(Event event, User subscriber);

    @Query(value =
            "SELECT count(subscription_number) as quantidade, indication_user_id, user_name " +
            "FROM tbl_subscription INNER JOIN tbl_user " +
            "ON tbl_subscription.indication_user_id = tbl_user.user_id " +
            "WHERE indication_user_id IS NOT NULL " +
            "AND event_id = :eventId " +
            "GROUP BY indication_user_id " +
            "ORDER BY quantidade DESC",
            nativeQuery = true)
    List<SubscriptionRankingItem> generateRanking(@Param("eventId") Integer eventId);
}
