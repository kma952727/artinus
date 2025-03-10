package com.artinus.membership.domain.subscription.model;

import com.artinus.membership.common.jpa.BaseEntity;
import com.artinus.membership.domain.subscription.model.vo.SubscribeState;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "subscription_history")
public class SubscriptionHistory extends BaseEntity {
    @Comment("사용된 채널")
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @Comment("사용된 구독 플랜")
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Comment("연관된 구독")
    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @Comment("새로운 구독 상태")
    @Column(name = "to_state")
    private SubscribeState toState;

    @Comment("이전 구독 상태")
    @Column(name = "from_ state")
    private SubscribeState fromState;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static SubscriptionHistory issue(
            @NonNull Subscription subscription,
            @NonNull Channel channel,
            @NonNull Plan plan,
            @NonNull SubscribeState fromState
    ) {
        var instance = new SubscriptionHistory();
        instance.channel = channel;
        instance.plan = plan;
        instance.subscription = subscription;
        instance.toState = plan.getState();
        instance.fromState = fromState;

        return instance;
    }
}
