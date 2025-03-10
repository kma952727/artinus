package com.artinus.membership.common.error.exception;

import static com.artinus.membership.common.error.ArtinusErrorCode.EXTERNAL_REQUEST__ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class ExternalRequestException extends ArtinusAbstractException {
    public <T> ExternalRequestException(String externalServiceName, T errorResponse) {
        super(
                "외부 api 요청에서 에러가 발생했습니다. 서비스: " + externalServiceName + ", 에러 응답: " + errorResponse,
                INTERNAL_SERVER_ERROR,
                EXTERNAL_REQUEST__ERROR
        );
    }
}
