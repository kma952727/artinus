package com.artinus.membership.common.dto;

public record ArtinusResponse <T>(
        String message,
        T data
) {
    public ArtinusResponse() {
        this("ok", null);
    }

    public ArtinusResponse(T data) {
        this("ok", data);
    }
}
