package com.artinus.membership.domain.subscription.model;

import com.artinus.membership.common.jpa.BaseEntity;
import com.artinus.membership.domain.subscription.component.ChannelTypeConverter;
import com.artinus.membership.domain.subscription.model.vo.ChannelType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import static com.artinus.membership.domain.subscription.model.vo.ChannelType.*;
import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Entity
@Getter
@Table(name = "channel")
public class Channel extends BaseEntity {
    @Comment("채널 이름")
    @Column(name = "name")
    private String name;

    @Comment("채널 종류 / 10: 구독, 30: 구독 해지, 60: 구독+구독 해지")
    @Convert(converter = ChannelTypeConverter.class)
    @Column(name = "channel_type")
    private ChannelType channelType;

    public Boolean isSupportSubscribe() {
        return channelType == ONLY_SUBSCRIBE || channelType == ALL;
    }
    public Boolean isSupportUnSubscribe() {
        return channelType == ONLY_UN_SUBSCRIBE || channelType == ALL;
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
