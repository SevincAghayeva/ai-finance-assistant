package com.portfolio.aifinanceassistant.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record Transaction(
        @NotBlank(message = "Description cannot be blank")
        String description,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be a positive number")
        BigDecimal amount,

        @NotBlank(message = "Category cannot be blank")
        String category,

        @NotNull(message = "Date is required")
        LocalDate date
) {}