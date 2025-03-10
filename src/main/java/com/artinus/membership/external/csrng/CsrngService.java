package com.artinus.membership.external.csrng;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CsrngService {
    private final CsrngFeignClient csrngFeignClient;

    public CsrngResponseDto requestCsrng() {
        return csrngFeignClient.requestCsrng(0, 1).getFirst();
    }
}
