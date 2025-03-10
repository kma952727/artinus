package com.artinus.membership.domain.subscription.error;

import com.artinus.membership.common.error.exception.ArtinusAbstractException;

import static com.artinus.membership.common.error.ArtinusErrorCode.UN_SUBSCRIBE_CANT_HAVE_PLAN;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class OnlyUnSubscribeCantHavePlanException extends ArtinusAbstractException {
    public OnlyUnSubscribeCantHavePlanException(String channelName) {
        super(
                "해당 채널은 플랜을 가질 수 없습니다. 채널명 -> " + channelName,
                BAD_REQUEST,
                UN_SUBSCRIBE_CANT_HAVE_PLAN
        );
    }
}
