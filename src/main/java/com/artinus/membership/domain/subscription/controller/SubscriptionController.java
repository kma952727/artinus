package com.artinus.membership.domain.subscription.controller;


import com.artinus.membership.common.dto.ArtinusResponse;
import com.artinus.membership.domain.subscription.dto.FindSubscriptionHistoryResponseDto;
import com.artinus.membership.domain.subscription.dto.SubscribeRequestDto;
import com.artinus.membership.domain.subscription.dto.UnSubscribeRequestDto;
import com.artinus.membership.domain.subscription.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Tag(name = "구독 관리")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @Operation(summary = "구독 요청")
    @PutMapping("/subscribes")
    public ResponseEntity<ArtinusResponse<Void>> subscribe(@Valid  @RequestBody SubscribeRequestDto request) {
        subscriptionService.subscribe(request.phoneNumber(), request.channelId());

        return new ResponseEntity<>(new ArtinusResponse<>(), NO_CONTENT);
    }

    @Operation(summary = "구독 해지 요청")
    @PutMapping("/un-subscribes")
    public ResponseEntity<ArtinusResponse<Void>> unsubscribe(@Valid @RequestBody UnSubscribeRequestDto request) {
        subscriptionService.unSubscribe(request.phoneNumber(), request.channelId());

        return new ResponseEntity<>(new ArtinusResponse<>(), NO_CONTENT);
    }

    @Operation(summary = "구독 이력 조회")
    @GetMapping("/subscription-histories")
    public ResponseEntity<ArtinusResponse<List<FindSubscriptionHistoryResponseDto>>> findAllSubscribes(
            @Parameter(description = "사용자 휴대폰 번호")
            @Pattern(regexp = "^01[0-9]-\\d{3,4}-\\d{4}$", message = "phoneNumber : 양식을 확인해주세요. (ex - 010-1234-5678)")
            @RequestParam
            String phoneNumber
    ) {
        var result = new ArtinusResponse<>(subscriptionService.findSubscriptionHistories(phoneNumber));

        return ResponseEntity.ok(result);
    }
}
