package com.artinus.membership.domain.subscription.component;

import com.artinus.membership.domain.subscription.model.vo.ChannelType;
import com.artinus.membership.domain.subscription.model.vo.SubscribeState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SubscribeStateConverter implements AttributeConverter<SubscribeState, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SubscribeState SubscribeState) {
        if (SubscribeState == null) {
            return null;
        }
        return SubscribeState.getValue();
    }

    @Override
    public SubscribeState convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return SubscribeState.ofOrThrow(dbData);
    }
}
