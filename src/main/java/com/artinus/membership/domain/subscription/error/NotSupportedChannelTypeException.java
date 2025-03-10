package com.artinus.membership.domain.subscription.error;

import com.artinus.membership.common.error.exception.ArtinusAbstractException;

import static com.artinus.membership.common.error.ArtinusErrorCode.NOT_SUPPORTED_CHANNEL_TYPE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class NotSupportedChannelTypeException extends ArtinusAbstractException {
    public NotSupportedChannelTypeException(String channelName, Boolean isSubscribe) {
        super(
                isSubscribe ?
                        "구독에 사용할 수 없는 채널입니다. 채널 -> " + channelName
                        : "구독 해지에 사용할 수 없는 채널입니다. 채널 -> " + channelName,
                BAD_REQUEST,
                NOT_SUPPORTED_CHANNEL_TYPE
        );
    }
}
