package com.artinus.membership.domain.subscription.error;

import com.artinus.membership.common.error.exception.ArtinusAbstractException;

import static com.artinus.membership.common.error.ArtinusErrorCode.INVALID__SUBSCRIPTION_TYPE_FOR_SUBSCRIBE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidSubscribeTypeException extends ArtinusAbstractException {
    public InvalidSubscribeTypeException(Long targetChannelId) {
        super(
                "구독/해지 요청을 완수할 수 없습니다. 타겟 채널 id -> " + targetChannelId,
                BAD_REQUEST,
                INVALID__SUBSCRIPTION_TYPE_FOR_SUBSCRIBE
        );
    }
}
