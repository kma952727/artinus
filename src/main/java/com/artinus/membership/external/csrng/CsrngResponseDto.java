package com.artinus.membership.external.csrng;

public record CsrngResponseDto(
        String status,
        Integer min,
        Integer max,
        Integer random
) { }
