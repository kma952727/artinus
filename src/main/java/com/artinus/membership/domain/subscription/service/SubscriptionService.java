package com.artinus.membership.domain.subscription.service;

import com.artinus.membership.common.error.exception.ExternalRequestException;
import com.artinus.membership.domain.subscription.dto.FindSubscriptionHistoryResponseDto;
import com.artinus.membership.domain.subscription.repository.SubscriptionHistoryJpaRepository;
import com.artinus.membership.domain.subscription.service.base.SubscriptionBaseService;
import com.artinus.membership.external.csrng.CsrngService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SubscriptionService {
    private final SubscriptionBaseService subscriptionBaseService;
    private final SubscriptionHistoryJpaRepository subscriptionHistoryJpaRepository;
    private final CsrngService csrngService;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public void subscribe(@NonNull String phoneNumber, @NonNull Long channelId) {
        var defaultPlan = subscriptionBaseService.findDefaultPlanByChannelId(channelId);
        var history = subscriptionBaseService.findByPhoneNumber(phoneNumber).subscribe(defaultPlan);

        var csrngResponse = csrngService.requestCsrng();
        if(csrngResponse.random() == 0) {
            throw new ExternalRequestException("csrng", csrngResponse);
        }

        publisher.publishEvent(history);
    }

    @Transactional
    public void unSubscribe(@NonNull String phoneNumber, @NonNull Long channelId) {
        var defaultPlan = subscriptionBaseService.findDefaultPlanByChannelId(channelId);
        var history = subscriptionBaseService.findByPhoneNumber(phoneNumber).unSubscribe(defaultPlan);

        var csrngResponse = csrngService.requestCsrng();
        if(csrngResponse.random() == 0) {
            throw new ExternalRequestException("csrng", csrngResponse);
        }

        publisher.publishEvent(history);
    }

    public List<FindSubscriptionHistoryResponseDto> findSubscriptionHistories(@NonNull String phoneNumber) {
        return subscriptionHistoryJpaRepository.findAllByPhoneNumber(phoneNumber)
                .stream()
                .map(FindSubscriptionHistoryResponseDto::new).toList();
    }
}