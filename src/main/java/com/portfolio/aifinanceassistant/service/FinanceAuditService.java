package com.portfolio.aifinanceassistant.service;

import com.portfolio.aifinanceassistant.dto.GroqRequest;
import com.portfolio.aifinanceassistant.dto.GroqResponse;
import com.portfolio.aifinanceassistant.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FinanceAuditService {

    private final RestClient groqRestClient;

    @Value("${groq.api-url}")
    private String apiUrl;

    public FinanceAuditService(RestClient groqRestClient) {
        this.groqRestClient = groqRestClient;
    }

    public String analyzeExpenses(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            return "No transaction data provided for analysis.";
        }

        log.info("Starting financial audit via Groq API for {} transactions.", transactions.size());

        String formattedTransactions = transactions.stream()
                .map(t -> String.format("- %s: %s (%s) Date: %s",
                        t.description(), t.amount(), t.category(), t.date()))
                .collect(Collectors.joining("\n"));

        GroqRequest requestBody = getGroqRequest(formattedTransactions);

        try {
            GroqResponse response = groqRestClient.post()
                    .uri(apiUrl)
                    .body(requestBody)
                    .retrieve()
                    .body(GroqResponse.class);

            if (response != null && response.choices() != null && !response.choices().isEmpty()) {
                return response.choices().get(0).message().content();
            }
            return "The AI engine did not return a response.";

        } catch (Exception e) {
            log.error("Network error occurred while communicating with Groq API: ", e);
            return "A technical error occurred during the financial audit: " + e.getMessage();
        }
    }

    private static GroqRequest getGroqRequest(String formattedTransactions) {
        String systemPrompt = """
                You are an expert Senior Auditor and Financial Analyst with international standards.
                Deeply analyze the provided expenses and generate a comprehensive report structured exactly as follows:
                
                # Personal Financial Audit Report
                
                ## 1. Overall Financial Health
                Provide a concise and sharp summary of the overall spending behavior.
                
                ## 2. Suspicious or Anomalous Expenses
                Highlight any excessive repetitions, unnecessary lookups, or anomalously high amounts in the same category (if none, state that everything looks normal).
                
                ## 3. Top 3 Golden Recommendations for Optimization
                Provide exactly 3 actionable, concrete advice to help the user save money and protect their budget.
                
                Rules: The response must be completely in English, professional, rigorous yet easy to understand. Use clean Markdown formatting only.
                """;

        String userPrompt = "Here is my recent financial statement:\n" + formattedTransactions;

        return new GroqRequest(
                "llama-3.3-70b-versatile",
                List.of(
                        new GroqRequest.Message("system", systemPrompt),
                        new GroqRequest.Message("user", userPrompt)
                ),
                0.7
        );
    }
}