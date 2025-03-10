package com.artinus.membership.domain.subscription.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public record UnSubscribeRequestDto(
        @Schema(description = "사용자 휴대폰 번호")
        @Pattern(regexp = "^01[0-9]-\\d{3,4}-\\d{4}$", message = "phoneNumber : 양식을 확인해주세요. (ex - 010-1234-5678)")
        String phoneNumber,

        @Schema(description = "구독 해지에 사용되는 채널 id")
        @Min(value = 1, message = "channelId : 1 이상")
        Long channelId
) { }