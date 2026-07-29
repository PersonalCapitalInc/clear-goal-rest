package com.cleargoal.monthlycycle.api;

import com.cleargoal.monthlycycle.domain.CycleStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public record MonthlyCycleResponse(
    Long id,
    String cycleMonth,
    LocalDate salaryDate,
    BigDecimal expectedAmount,
    BigDecimal availableAmount,
    BigDecimal actualInvestedAmount,
    LocalDate investedOn,
    CycleStatus status,
    String notes
) {
}
