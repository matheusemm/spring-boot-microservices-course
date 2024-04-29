package com.sivalabs.bookstore.notification.domain.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record OrderItem(
        @NotBlank(message = "Item code is required") String code,
        @NotBlank(message = "Item name is required") String name,
        @NotNull(message = "Item price is required") BigDecimal price,
        @NotNull @Min(1) Integer quantity) {}
