package com.portfolio.aifinanceassistant.dto;

import java.util.List;

public record GroqRequest(
        String model,
        List<Message> messages,
        double temperature
) {
    public record Message(String role, String content) {}
}