package com.artinus.membership.domain.subscription.model;

import com.artinus.membership.domain.member.model.Member;
import com.artinus.membership.domain.subscription.error.InvalidSubscribeStateException;
import com.artinus.membership.domain.subscription.error.NotSupportedChannelTypeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.artinus.membership.domain.subscription.model.vo.ChannelType.*;
import static com.artinus.membership.domain.subscription.model.vo.SubscribeState.*;
import static org.junit.jupiter.api.Assertions.*;

public class SubscriptionTests {
    @Nested
    @DisplayName("사용자는 구독할 수 있다.")
    class Subscribe {
        @Test
        @DisplayName("PREMIUM 상태로 구독할 수 있다.")
        public void subscribeToPremium() {
            // ginve
            var member = new Member("010-1111-1111");

            var callCenterChannel = new Channel("CALL_CENTER", ALL);
            var oldPlan = new Plan("기본 회원", true, callCenterChannel, NONE, 3);

            var ktChannel = new Channel("KT", ALL);
            var newPlan = new Plan("전자책 월 정기구독", true, ktChannel, PREMIUM, 3);

            var sut = new Subscription(member, ktChannel, oldPlan);

            // when
            var history = sut.subscribe(newPlan);

            // then
            assertAll(
                    () -> assertEquals(newPlan, sut.getPlan()),
                    () -> assertEquals(ktChannel, sut.getChannel()),
                    () -> assertEquals(newPlan, history.getPlan()),
                    () -> assertEquals(ktChannel, history.getChannel())
            );
        }

        @Test
        @DisplayName("구독 해지 전용 채널로 구독할 수 없다.")
        public void notSubscribeByUnsubscribeChannel() {
            // ginve
            var member = new Member("010-1111-1111");
            var callCenterChannel = new Channel("CALL_CENTER", ALL);
            var oldPlan = new Plan("기본 회원", true, callCenterChannel, NONE, 3);

            var mailChannel = new Channel("MAIL", ONLY_UN_SUBSCRIBE);
            var newPlan = new Plan("전자책 월 정기구독", true, mailChannel, NORMAL, 3);

            var sut = new Subscription(member, callCenterChannel, oldPlan);

            // when - then
            assertThrows(NotSupportedChannelTypeException.class, () ->sut.subscribe(newPlan));
        }

        @Test
        @DisplayName("NONE상태로 구독할 수 없다.")
        public void notPremiumToNone() {
            // ginve
            var member = new Member("010-1111-1111");
            var homePageChannel = new Channel("PAGE", ALL);
            var oldPlan = new Plan("기본 회원", true, homePageChannel, NORMAL, 3);

            var ktChannel = new Channel("KT", ONLY_SUBSCRIBE);
            var newPlan = new Plan("전자책 월 정기구독", true, ktChannel, NONE, 3);

            var sut = new Subscription(member, homePageChannel, oldPlan);

            // when - then
            assertThrows(InvalidSubscribeStateException.class, () ->sut.subscribe(newPlan));
        }

        @Test
        @DisplayName("PREMIUM 상태에서 NORMAL상태로 구독할 수 없다.")
        public void notPremiumToNormal() {
            // ginve
            var member = new Member("010-1111-1111");
            var homePageChannel = new Channel("PAGE", ALL);
            var oldPlan = new Plan("기본 회원", true, homePageChannel, PREMIUM, 3);

            var ktChannel = new Channel("KT", ONLY_SUBSCRIBE);
            var newPlan = new Plan("전자책 월 정기구독", true, ktChannel, NORMAL, 3);

            var sut = new Subscription(member, homePageChannel, oldPlan);

            // when - then
            assertThrows(InvalidSubscribeStateException.class, () ->sut.subscribe(newPlan));
        }
    }

    @Nested
    @DisplayName("사용자는 구독을 해지할 수 있다.")
    class Unsubscribe {
        @Test
        @DisplayName("NORMAL 상태로 구독 해지할 수 있다.")
        public void unsubscribeToNormal() {
            // given
            var member = new Member("010-1111-1111");

            var ktChannel = new Channel("KT", ALL);
            var oldPlan = new Plan("전자책 월 정기구독", true, ktChannel, PREMIUM, 3);

            var callCenterChannel = new Channel("CALL_CENTER", ALL);
            var newPlan = new Plan("기본 회원", true, callCenterChannel, NONE, 3);

            var sut = new Subscription(member, ktChannel, oldPlan);

            // when
            var history = sut.unSubscribe(newPlan);

            // then
            assertAll(
                    () -> assertEquals(newPlan, sut.getPlan()),
                    () -> assertEquals(callCenterChannel, sut.getChannel()),
                    () -> assertEquals(newPlan, history.getPlan()),
                    () -> assertEquals(callCenterChannel, history.getChannel())
            );
        }

        @Test
        @DisplayName("구독 전용 채널로 구독 해지할 수 없다.")
        public void notUnsubscribeBySubscribeChannel() {
            // given
            var member = new Member("010-1111-1111");

            var ktChannel = new Channel("KT", ALL);
            var oldPlan = new Plan("전자책 월 정기구독", true, ktChannel, PREMIUM, 3);

            var naverChannel = new Channel("NAVER", ONLY_SUBSCRIBE);
            var newPlan = new Plan("기본 회원", true, naverChannel, NONE, 3);

            var sut = new Subscription(member, ktChannel, oldPlan);

            // when, then
            assertThrows(NotSupportedChannelTypeException.class, () -> sut.unSubscribe(newPlan));
        }

        @Test
        @DisplayName("PREMIUM 상태로 구독 해지할 수 없다.")
        public void notPremiumToPremium() {
            // given
            var member = new Member("010-1111-1111");

            var ktChannel = new Channel("KT", ALL);
            var oldPlan = new Plan("전자책 월 정기구독", true, ktChannel, PREMIUM, 3);

            var naverChannel = new Channel("NAVER", ONLY_UN_SUBSCRIBE);
            var newPlan = new Plan("기본 회원", true, naverChannel, PREMIUM, 3);

            var sut = new Subscription(member, ktChannel, oldPlan);

            // when, then
            assertThrows(InvalidSubscribeStateException.class, () -> sut.unSubscribe(newPlan));
        }
    }
}
