package com.portfolio.aifinanceassistant.controller;

import com.portfolio.aifinanceassistant.model.Transaction;
import com.portfolio.aifinanceassistant.service.FinanceAuditService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/finance")
public class FinanceAuditController {

    private final FinanceAuditService auditService;

    @PostMapping("/audit")
    public ResponseEntity<String> runAudit(@Valid @RequestBody List<Transaction> transactions) {
        String report = auditService.analyzeExpenses(transactions);
        return ResponseEntity.ok(report);
    }
}