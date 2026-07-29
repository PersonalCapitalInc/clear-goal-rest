package com.cleargoal.goal.application;

import com.cleargoal.goal.api.GoalProgressResponse;
import com.cleargoal.settings.application.SettingsService;
import com.cleargoal.settings.domain.SettingsProfile;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GoalProgressService {

    private final SettingsService settingsService;

    public GoalProgressService(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    public GoalProgressResponse getProgress() {
        SettingsProfile settings = settingsService.loadSettings();
        BigDecimal estimatedGains = settings.getCurrentCorpus().subtract(settings.getTotalInvestedCapital());
        BigDecimal completionPercentage = settings.getCurrentCorpus()
            .multiply(BigDecimal.valueOf(100))
            .divide(settings.getGoalAmount(), 2, RoundingMode.HALF_UP);
        BigDecimal remainingAmount = settings.getGoalAmount().subtract(settings.getCurrentCorpus()).max(BigDecimal.ZERO);
        int projectedMonths = projectMonthsToGoal(settings, remainingAmount);

        return new GoalProgressResponse(
            settings.getGoalAmount(),
            settings.getCurrentCorpus(),
            settings.getTotalInvestedCapital(),
            estimatedGains,
            completionPercentage,
            remainingAmount,
            projectedMonths
        );
    }

    private int projectMonthsToGoal(SettingsProfile settings, BigDecimal remainingAmount) {
        if (remainingAmount.signum() == 0) {
            return 0;
        }
        BigDecimal monthlyAmount = settings.getDefaultMonthlyAmount();
        if (monthlyAmount.signum() <= 0) {
            return Integer.MAX_VALUE;
        }
        return remainingAmount.divide(monthlyAmount, 0, RoundingMode.CEILING).intValue();
    }
}
