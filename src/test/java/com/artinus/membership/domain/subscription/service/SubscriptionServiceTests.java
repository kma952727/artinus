package com.artinus.membership.domain.subscription.service;

import com.artinus.membership.common.error.exception.ExternalRequestException;
import com.artinus.membership.domain.member.model.Member;
import com.artinus.membership.external.csrng.CsrngResponseDto;
import com.artinus.membership.external.csrng.CsrngService;
import com.artinus.membership.helper.ServiceLayerTestTemplate;
import com.artinus.membership.domain.subscription.model.Channel;
import com.artinus.membership.domain.subscription.model.Plan;
import com.artinus.membership.domain.subscription.model.Subscription;
import com.artinus.membership.domain.subscription.model.SubscriptionHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static com.artinus.membership.domain.subscription.model.vo.ChannelType.*;
import static com.artinus.membership.domain.subscription.model.vo.SubscribeState.*;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class SubscriptionServiceTests extends ServiceLayerTestTemplate {
    @Autowired
    private SubscriptionService sut;

    @MockitoBean
    private CsrngService csrngService;

    @Nested
    @DisplayName("구독을 할 수 있다.")
    class Subscribe {
        @Test
        @DisplayName("NONE -> PREMIUM상태로 구독할 수 있다.")
        public void subscribePremium() {
            // given
            var member = new Member("010-1111-1111");
            var ktChannel = new Channel("KT", ALL);
            var callCenterChannel = new Channel("CALL_CENTER", ALL);
            var oldPlan = new Plan("기본 회원", true, callCenterChannel, NONE, 3);
            var newPlan = new Plan("전자책 월 정기구독", true, ktChannel, PREMIUM, 3);
            var subscription = new Subscription(member, callCenterChannel, oldPlan);

            memberJpaRepository.save(member);
            channelJpaRepository.saveAll(of(ktChannel, callCenterChannel));
            planJpaRepository.saveAll(of(oldPlan, newPlan));
            subscriptionJpaRepository.save(subscription);

            when(csrngService.requestCsrng()).thenReturn(new CsrngResponseDto("z", 1, 1, 1));

            // when
            sut.subscribe("010-1111-1111", 1L);

            // then
            var savedHistories = subscriptionHistoryJpaRepository.findAll();
            var savedSubscription = subscriptionJpaRepository.findById(1L).get();
            assertAll(
                    () -> assertEquals(1, savedHistories.size()),
                    () -> assertEquals(newPlan, savedSubscription.getPlan()),
                    () -> assertEquals(ktChannel, savedSubscription.getChannel()),
                    () -> assertEquals(member, savedSubscription.getSubscriber())
            );
        }

        @Test
        @DisplayName("csrng api 통신에 실패하면 롤백한다.")
        public void throwApiException() {
            // given
            var member = new Member("010-1111-1111");
            var ktChannel = new Channel("KT", ALL);
            var callCenterChannel = new Channel("CALL_CENTER", ALL);
            var oldPlan = new Plan("기본 회원", true, callCenterChannel, NONE, 3);
            var newPlan = new Plan("전자책 월 정기구독", true, ktChannel, PREMIUM, 3);
            var subscription = new Subscription(member, callCenterChannel, oldPlan);

            memberJpaRepository.save(member);
            channelJpaRepository.saveAll(of(ktChannel, callCenterChannel));
            planJpaRepository.saveAll(of(oldPlan, newPlan));
            subscriptionJpaRepository.save(subscription);

            when(csrngService.requestCsrng()).thenReturn(new CsrngResponseDto("z", 1, 1, 0));

            // when
            assertThrows(ExternalRequestException.class, () -> sut.subscribe("010-1111-1111", 1L));

            // then
            var savedHistories = subscriptionHistoryJpaRepository.findAll();
            var savedSubscription = subscriptionJpaRepository.findById(1L).get();

            assertAll(
                    () -> assertEquals(0, savedHistories.size()),
                    () -> assertEquals(callCenterChannel, savedSubscription.getChannel()),
                    () -> assertEquals(oldPlan, savedSubscription.getPlan())
            );
        }
    }

    @Nested
    @DisplayName("구독 이력을 조회할 수 있다.")
    class FindSubscriptionHistories {
        @Test
        @DisplayName("구독 이력 3개를 조회할 수 있다.")
        public void find_3() {
            // given
            var member = new Member("010-1111-1111");
            var ktChannel = new Channel("KT", ALL);
            var callCenterChannel = new Channel("CALL CENTER", ONLY_UN_SUBSCRIBE);
            var plan1 = new Plan("전자책 월 정기구독", null, ktChannel, PREMIUM, 1);
            var plan2 = new Plan("콜센터-NORMAL", null, callCenterChannel, NORMAL, 1);
            var plan3 = new Plan("콜센터-NONE", null, callCenterChannel, NONE, 1);
            var subscription = new Subscription(member, callCenterChannel, plan2);
            var history1 = new SubscriptionHistory(ktChannel, plan1, subscription, NONE, PREMIUM);
            var history2 = new SubscriptionHistory(ktChannel, plan2, subscription, PREMIUM, NORMAL);
            var history3 = new SubscriptionHistory(ktChannel, plan3, subscription, NORMAL, NONE);

            memberJpaRepository.save(member);
            channelJpaRepository.saveAll(of(ktChannel, callCenterChannel));
            planJpaRepository.saveAll(of(plan1, plan2, plan3));
            subscriptionJpaRepository.save(subscription);
            subscriptionHistoryJpaRepository.saveAll(of(history1, history2, history3));

            // when
            var result = sut.findSubscriptionHistories(member.getPhoneNumber());

            // then
            assertAll(
                    () -> assertEquals(3, result.size()),
                    () -> assertEquals(history1.getId(), result.getFirst().historyId()),
                    () -> assertEquals(history3.getId(), result.getLast().historyId())
            );
        }

        @Test
        @DisplayName("자신의 구독 이력 1개 조회할 수 있다.")
        public void find_1() {
            // given
            var member1 = new Member("010-1111-1111");
            var member2 = new Member("010-4242-1523");
            var ktChannel = new Channel("KT", ALL);
            var callCenterChannel = new Channel("CALL CENTER", ONLY_UN_SUBSCRIBE);
            var plan1 = new Plan("전자책 월 정기구독", null, ktChannel, PREMIUM, 1);
            var plan2 = new Plan("전화 구독-NORMAL", null, callCenterChannel, NORMAL, 3);
            var plan3 = new Plan("전화 구독-NONE", null, callCenterChannel, NONE, 3);
            var subscription1 = new Subscription(member1, callCenterChannel, plan2);
            var subscription2 = new Subscription(member2, callCenterChannel, plan2);
            var history1 = new SubscriptionHistory(ktChannel, plan1, subscription1, NONE, PREMIUM);
            var history2 = new SubscriptionHistory(ktChannel, plan2, subscription2, PREMIUM, NORMAL);
            var history3 = new SubscriptionHistory(ktChannel, plan3, subscription2, NORMAL, NONE);

            memberJpaRepository.saveAll(of(member1, member2));
            channelJpaRepository.saveAll(of(ktChannel, callCenterChannel));
            planJpaRepository.saveAll(of(plan1, plan2, plan3));
            subscriptionJpaRepository.saveAll(of(subscription1, subscription2));
            subscriptionHistoryJpaRepository.saveAll(of(history1, history2, history3));

            // when
            var result = sut.findSubscriptionHistories(member1.getPhoneNumber());

            // then
            assertAll(
                    () -> assertEquals(1, result.size()),
                    () -> assertEquals(history1.getId(), result.getFirst().historyId())
            );
        }

        @Test
        @DisplayName("구독 이력이 없다면 0개를 조회한다.")
        public void find_0() {
            // when
            var result = sut.findSubscriptionHistories("010-8273-1515");

            // then
            assertEquals(0, result.size());
        }
    }
}
