package com.my.demo.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        UriTemplateHandler template = new RootUriTemplateHandler("http://localhost:8080/payments");
        return builder
                .setReadTimeout(Duration.ofSeconds(2))
                .uriTemplateHandler(template)
                .build();
    }
}
