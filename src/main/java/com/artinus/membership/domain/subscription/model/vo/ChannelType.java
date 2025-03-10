package com.artinus.membership.domain.subscription.model.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum ChannelType {
    ONLY_SUBSCRIBE(10),
    ONLY_UN_SUBSCRIBE(30),
    ALL(60);

    private final Integer value;

    public static ChannelType ofOrThrow(@NotNull Integer value) {
        return Arrays.stream(ChannelType.values())
                .filter(type -> Objects.equals(type.value, value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채널 타입입니다."));
    }
}
