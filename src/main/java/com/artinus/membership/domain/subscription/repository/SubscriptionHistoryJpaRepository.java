package com.artinus.membership.domain.subscription.repository;

import com.artinus.membership.domain.subscription.model.SubscriptionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionHistoryJpaRepository extends JpaRepository<SubscriptionHistory, Long> {
    @Query("""
            select history
            from SubscriptionHistory history
            join fetch history.channel channel
            join fetch history.plan plan
            join fetch history.subscription subscription
            join fetch subscription.subscriber subscriber
            where subscriber.phoneNumber = :phoneNumber
            """
    )
    List<SubscriptionHistory> findAllByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
