package com.artinus.membership.common.dto;

import com.artinus.membership.common.error.ArtinusErrorCode;

public record ArtinuseErrorResponse(
        ArtinusErrorCode errorCode,
        String message
) { }
