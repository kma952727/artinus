package com.artinus.membership.domain.subscription.error;

import com.artinus.membership.common.error.exception.ArtinusAbstractException;

import static com.artinus.membership.common.error.ArtinusErrorCode.NOT_FOUND__PlAN_IN_CHANNEL;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundPlanInChannelException extends ArtinusAbstractException {
    public NotFoundPlanInChannelException(Long channelId) {
        super(
                "해당 채널에 플랜이 존재하지 않습니다. 채널 ->" + channelId,
                NOT_FOUND,
                NOT_FOUND__PlAN_IN_CHANNEL
        );
    }
}
