package com.oracle.example.infrastructureapi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient
public interface Client {

    @GetMapping("/a/query")
    Integer query();
}
