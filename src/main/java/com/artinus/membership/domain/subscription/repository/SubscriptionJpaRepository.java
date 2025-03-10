package com.artinus.membership.domain.subscription.repository;

import com.artinus.membership.domain.subscription.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubscriptionJpaRepository extends JpaRepository<Subscription, Long> {
    @Query("""
            select subscription
            from Subscription subscription
            join fetch subscription.subscriber subscriber
            join fetch subscription.plan plan
            join fetch subscription.channel channel
            where subscriber.phoneNumber = :phoneNumber
            """
    )
    Optional<Subscription> findByPhoneNumber(String phoneNumber);
}
