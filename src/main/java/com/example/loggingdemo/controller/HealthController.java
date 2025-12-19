package com.example.loggingdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class HealthController {

    private static final Logger log = LoggerFactory.getLogger(HealthController.class);

    @GetMapping("/health")
    public String health() {
        // Generate correlation ID for this request
        String correlationId = UUID.randomUUID().toString();
        MDC.put("correlationId", correlationId);

        log.info("Health check invoked | service=loggingdemo | version=1.0.0");

        MDC.clear();
        return "Application is running";
    }

    @GetMapping("/error-test")
    public String errorTest() {
        // Generate orchestration ID for simulated error
        String orchestrationId = UUID.randomUUID().toString();
        MDC.put("orchestrationId", orchestrationId);

        log.error("Simulated application error | service=loggingdemo | version=1.0.0 | orchestrationId={}", orchestrationId);

        MDC.clear();
        throw new RuntimeException("Simulated failure");
    }
}