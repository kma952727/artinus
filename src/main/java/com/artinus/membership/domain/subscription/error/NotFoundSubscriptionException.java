package com.artinus.membership.domain.subscription.error;

import com.artinus.membership.common.error.exception.ArtinusAbstractException;

import static com.artinus.membership.common.error.ArtinusErrorCode.NOT_FOUND__SUBSCRIPTION;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundSubscriptionException extends ArtinusAbstractException {
    public NotFoundSubscriptionException(String field, String key) {
        super(
                "존재하지 않는 구독입니다. -> " + field + ", " + key,
                NOT_FOUND,
                NOT_FOUND__SUBSCRIPTION
        );
    }
}
