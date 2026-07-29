package com.cleargoal.settings.api;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record UpdateCorpusValueRequest(
    @NotNull @DecimalMin("0.00") BigDecimal currentCorpus
) {
}
