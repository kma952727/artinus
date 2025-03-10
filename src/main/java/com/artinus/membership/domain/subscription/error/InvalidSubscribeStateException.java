package com.artinus.membership.domain.subscription.error;

import com.artinus.membership.domain.subscription.model.vo.SubscribeState;
import com.artinus.membership.common.error.exception.ArtinusAbstractException;

import static com.artinus.membership.common.error.ArtinusErrorCode.INVALID__SUBSCRIPTION_STATE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidSubscribeStateException extends ArtinusAbstractException {
    public InvalidSubscribeStateException(SubscribeState currentState, SubscribeState newState) {
        super(
                "구독할 수 없습니다. 현재 상태" + currentState + ", 새로운 상태" + newState,
                BAD_REQUEST,
                INVALID__SUBSCRIPTION_STATE
        );
    }
}
