package com.artinus.membership.domain.subscription.model;

import com.artinus.membership.domain.member.model.Member;
import com.artinus.membership.domain.subscription.error.InvalidSubscribeStateException;
import com.artinus.membership.domain.subscription.error.NotSupportedChannelTypeException;
import com.artinus.membership.common.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import static com.artinus.membership.domain.subscription.model.vo.SubscribeState.*;
import static java.util.List.of;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
@Table(name = "subscription")
public class Subscription extends BaseEntity {
    @Comment("구독자")
    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member subscriber;

    @Comment("사용된 구독 플랜")
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @Comment("사용된 구독 플랜")
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    public SubscriptionHistory subscribe(@NonNull Plan newPlan) {
        if(!newPlan.getChannel().isSupportSubscribe()) {
            throw new NotSupportedChannelTypeException(plan.getChannel().getName(), true);
        }

        var currentState = plan.getState();
        switch (newPlan.getState()) {
            case NORMAL:
                if(currentState != NONE) {
                    throw new InvalidSubscribeStateException(currentState, newPlan.getState());
                }
                break;

                case PREMIUM:
                if(!of(NONE, NORMAL).contains(currentState)) {
                    throw new InvalidSubscribeStateException(currentState, newPlan.getState());
                }
                break;

            default:
                throw new InvalidSubscribeStateException(currentState, newPlan.getState());
        }

        channel = newPlan.getChannel();
        plan = newPlan;

        var subscriptionHistory = SubscriptionHistory.issue(this, channel, plan, currentState);
        return requireNonNull(subscriptionHistory);
    }

    public SubscriptionHistory unSubscribe(@NonNull Plan newPlan) {
        if(!newPlan.getChannel().isSupportUnSubscribe()) {
            throw new NotSupportedChannelTypeException(plan.getChannel().getName(), false);
        }

        var currentState = plan.getState();
        switch (newPlan.getState()) {
            case NORMAL:
                if(currentState != PREMIUM) {
                    throw new InvalidSubscribeStateException(currentState, newPlan.getState());
                }
                break;

            case NONE:
                if(!of(NORMAL, PREMIUM).contains(currentState)) {
                    throw new InvalidSubscribeStateException(currentState, newPlan.getState());
                }
                break;

            default:
                throw new InvalidSubscribeStateException(currentState, newPlan.getState());
        }

        channel = newPlan.getChannel();
        plan = newPlan;

        var history = SubscriptionHistory.issue(this, channel, plan, currentState);
        return requireNonNull(history);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}