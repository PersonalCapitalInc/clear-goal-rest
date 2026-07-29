package com.cleargoal.settings.api;

import com.cleargoal.settings.domain.RiskPreference;
import java.math.BigDecimal;

public record SettingsResponse(
    Long id,
    BigDecimal goalAmount,
    BigDecimal currentCorpus,
    BigDecimal totalInvestedCapital,
    BigDecimal defaultMonthlyAmount,
    BigDecimal annualStepUpPercentage,
    Integer salaryDay,
    RiskPreference riskPreference,
    String timeZone,
    String telegramChatId
) {
}
