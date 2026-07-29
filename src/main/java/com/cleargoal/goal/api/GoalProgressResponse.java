package com.cleargoal.goal.api;

import java.math.BigDecimal;

public record GoalProgressResponse(
    BigDecimal goalAmount,
    BigDecimal currentCorpus,
    BigDecimal totalInvestedCapital,
    BigDecimal estimatedGains,
    BigDecimal completionPercentage,
    BigDecimal remainingAmount,
    Integer projectedMonthsToGoal
) {
}
