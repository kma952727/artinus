package com.artinus.membership.external.csrng;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "csrng", url = "https://csrng.net/csrng/csrng.php")
public interface CsrngFeignClient {
    @GetMapping
    List<CsrngResponseDto> requestCsrng(@RequestParam Integer min, @RequestParam Integer max);
}
