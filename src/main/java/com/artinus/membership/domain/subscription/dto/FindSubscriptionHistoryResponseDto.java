package com.artinus.membership.domain.subscription.dto;

import com.artinus.membership.domain.subscription.model.SubscriptionHistory;
import com.artinus.membership.domain.subscription.model.vo.SubscribeState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record FindSubscriptionHistoryResponseDto(
        @Schema(description = "구독 이력 id")
        Long historyId,

        @Schema(description = "채널 id")
        Long channelId,

        @Schema(description = "구독 날짜")
        LocalDateTime issuedAt,

        @Schema(description = "해지 날짜")
        LocalDateTime endedAt
) {
    public FindSubscriptionHistoryResponseDto(SubscriptionHistory entity) {
        this(
                entity.getId(),
                entity.getChannel().getId(),
                entity.getCreatedAt(),
                entity.getCreatedAt().plusMonths(entity.getPlan().getPeriod())
        );
    }
}
