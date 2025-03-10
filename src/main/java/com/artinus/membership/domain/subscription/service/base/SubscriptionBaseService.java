package com.artinus.membership.domain.subscription.service.base;

import com.artinus.membership.domain.subscription.error.NotFoundPlanInChannelException;
import com.artinus.membership.domain.subscription.error.NotFoundSubscriptionException;
import com.artinus.membership.domain.subscription.model.Plan;
import com.artinus.membership.domain.subscription.model.Subscription;
import com.artinus.membership.domain.subscription.repository.ChannelJpaRepository;
import com.artinus.membership.domain.subscription.repository.PlanJpaRepository;
import com.artinus.membership.domain.subscription.repository.SubscriptionJpaRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionBaseService {
    private final SubscriptionJpaRepository subscriptionJpaRepository;
    private final PlanJpaRepository planJpaRepository;

    public Subscription findByPhoneNumber(@NonNull String phoneNumber) {
        var subscription = subscriptionJpaRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundSubscriptionException("phoneNumber", phoneNumber));

        return requireNonNull(subscription);
    }

    public Plan findDefaultPlanByChannelId(@NonNull Long channelId) {
        var plan = planJpaRepository.findByChannelId(channelId).orElseThrow(() -> new NotFoundPlanInChannelException(channelId));

        return requireNonNull(plan);
    }
}
