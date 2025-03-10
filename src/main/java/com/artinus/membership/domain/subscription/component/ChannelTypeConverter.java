package com.artinus.membership.domain.subscription.component;

import com.artinus.membership.domain.subscription.model.vo.ChannelType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ChannelTypeConverter implements AttributeConverter<ChannelType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ChannelType subscribeType) {
        if (subscribeType == null) {
            return null;
        }
        return subscribeType.getValue();
    }

    @Override
    public ChannelType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return ChannelType.ofOrThrow(dbData);
    }
}
