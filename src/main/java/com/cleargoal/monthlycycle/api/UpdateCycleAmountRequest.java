package com.cleargoal.monthlycycle.api;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record UpdateCycleAmountRequest(
    @NotNull @DecimalMin("0.00") BigDecimal availableAmount
) {
}
