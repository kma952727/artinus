package com.artinus.membership.domain.subscription.model.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum SubscribeState {
    NONE(10),
    NORMAL(350),
    PREMIUM(780);

    private final Integer value;

    public static SubscribeState ofOrThrow(@NotNull Integer value) {
        return Arrays.stream(SubscribeState.values())
                .filter(type -> Objects.equals(type.value, value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상태입니다."));
    }
}
