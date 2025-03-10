package com.artinus.membership.domain.subscription.error;

import com.artinus.membership.common.error.exception.ArtinusAbstractException;

import static com.artinus.membership.common.error.ArtinusErrorCode.NOT_FOUND__CHANNEL;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundChannelException extends ArtinusAbstractException {
    public NotFoundChannelException(Long channelId) {
        super(
                "존재하지 않는 채널입니다. -> " + channelId,
                NOT_FOUND,
                NOT_FOUND__CHANNEL
        );
    }
}
