package com.artinus.membership.domain.subscription.error;

import com.artinus.membership.common.error.exception.ArtinusAbstractException;

import static com.artinus.membership.common.error.ArtinusErrorCode.INVALID__REQUEST_DATA;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidSubscribePeriodException extends ArtinusAbstractException {
    public InvalidSubscribePeriodException(Integer period) {
        super("구독 기간은 1 이상이여야 합니다. 기간 ->" + period, BAD_REQUEST, INVALID__REQUEST_DATA);
    }
}
