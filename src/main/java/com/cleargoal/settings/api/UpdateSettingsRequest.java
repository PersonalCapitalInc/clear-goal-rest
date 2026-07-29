package com.cleargoal.settings.api;

import com.cleargoal.settings.domain.RiskPreference;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record UpdateSettingsRequest(
    @NotNull @DecimalMin("1.00") BigDecimal goalAmount,
    @NotNull @DecimalMin("0.00") BigDecimal currentCorpus,
    @NotNull @DecimalMin("0.00") BigDecimal totalInvestedCapital,
    @NotNull @DecimalMin("1.00") BigDecimal defaultMonthlyAmount,
    @NotNull @DecimalMin("0.00") BigDecimal annualStepUpPercentage,
    @NotNull @Min(1) @Max(31) Integer salaryDay,
    @NotNull RiskPreference riskPreference,
    @NotBlank String timeZone,
    String telegramChatId
) {
}
