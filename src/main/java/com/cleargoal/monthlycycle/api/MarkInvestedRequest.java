package com.cleargoal.monthlycycle.api;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record MarkInvestedRequest(
    @NotNull @DecimalMin("0.00") BigDecimal actualInvestedAmount,
    LocalDate investedOn,
    String notes
) {
}
