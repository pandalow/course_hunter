package com.hunt.service.impl;

import com.hunt.dto.FastApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class FastApiClientServiceImpl {
    private final WebClient webClient;

    public FastApiClientServiceImpl(WebClient.Builder webClient) {
        this.webClient = WebClient.builder().baseUrl("http://127.0.0.1:8000").build();
    }

    public Mono<FastApiResponse> getFastApiResult(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search/{query}")
                        .build(query))
                .retrieve()
                .bodyToMono(FastApiResponse.class)
                .timeout(java.time.Duration.ofSeconds(10));
    }

}
