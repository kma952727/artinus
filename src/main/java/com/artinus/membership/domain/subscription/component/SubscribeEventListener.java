package com.artinus.membership.domain.subscription.component;

import com.artinus.membership.domain.subscription.model.SubscriptionHistory;
import com.artinus.membership.domain.subscription.repository.SubscriptionHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Slf4j
@RequiredArgsConstructor
@Component
public class SubscribeEventListener {
    private final SubscriptionHistoryJpaRepository subscriptionHistoryJpaRepository;

    @Async
    @TransactionalEventListener
    @Transactional(propagation = REQUIRES_NEW)
    public void pushHistory(SubscriptionHistory history) {
        log.info("SubscribeEventListener :: pushSubscribeHistory :: history ::: {}", history);
        subscriptionHistoryJpaRepository.save(history);
    }
}