package com.artinus.membership.domain.subscription.model;

import com.artinus.membership.common.jpa.BaseEntity;
import com.artinus.membership.domain.subscription.component.SubscribeStateConverter;
import com.artinus.membership.domain.subscription.model.vo.SubscribeState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Entity
@Getter
@Table(name = "plan")
public class Plan extends BaseEntity {
    @Comment("플랜 이름")
    @Column(name = "name")
    private String name;

    @Comment("기본 플랜 여부")
    @Column(name = "is_default")
    private Boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @Comment("구독 상태")
    @Convert(converter = SubscribeStateConverter.class)
    @Column(name = "subscribe_state")
    private SubscribeState state;

    @Comment("구독 기간")
    @Column(name = "period")
    private Integer period;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
