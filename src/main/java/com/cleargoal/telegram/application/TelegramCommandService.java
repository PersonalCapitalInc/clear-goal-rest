package com.cleargoal.telegram.application;

import com.cleargoal.goal.api.GoalProgressResponse;
import com.cleargoal.goal.application.GoalProgressService;
import com.cleargoal.monthlycycle.api.MarkInvestedRequest;
import com.cleargoal.monthlycycle.api.MonthlyCycleResponse;
import com.cleargoal.monthlycycle.api.UpdateCycleAmountRequest;
import com.cleargoal.monthlycycle.application.MonthlyCycleService;
import com.cleargoal.settings.api.SettingsResponse;
import com.cleargoal.settings.api.UpdateCorpusValueRequest;
import com.cleargoal.settings.application.SettingsService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@ConditionalOnProperty(prefix = "cleargoal.telegram", name = "enabled", havingValue = "true")
public class TelegramCommandService {

    private final SettingsService settingsService;
    private final MonthlyCycleService monthlyCycleService;
    private final GoalProgressService goalProgressService;

    public TelegramCommandService(
        SettingsService settingsService,
        MonthlyCycleService monthlyCycleService,
        GoalProgressService goalProgressService
    ) {
        this.settingsService = settingsService;
        this.monthlyCycleService = monthlyCycleService;
        this.goalProgressService = goalProgressService;
    }

    public String handleMessage(String text) {
        if (!StringUtils.hasText(text)) {
            return helpText();
        }

        String trimmed = text.trim();
        if (trimmed.equals("/start") || trimmed.equals("/help")) {
            return helpText();
        }
        if (trimmed.equals("/plan")) {
            return formatPlan(monthlyCycleService.getCurrentCycle());
        }
        if (trimmed.equals("/progress")) {
            return formatProgress(goalProgressService.getProgress());
        }
        if (trimmed.equals("/settings")) {
            return formatSettings(settingsService.getSettings());
        }
        if (trimmed.startsWith("/amount ")) {
            BigDecimal amount = parseAmount(trimmed.substring("/amount ".length()));
            return formatPlan(monthlyCycleService.updateAvailableAmount(new UpdateCycleAmountRequest(amount)));
        }
        if (trimmed.equals("/invested")) {
            MonthlyCycleResponse cycle = monthlyCycleService.getCurrentCycle();
            return formatPlan(monthlyCycleService.markInvested(
                new MarkInvestedRequest(cycle.availableAmount(), LocalDate.now(), "Marked invested from Telegram")));
        }
        if (trimmed.startsWith("/invested ")) {
            BigDecimal amount = parseAmount(trimmed.substring("/invested ".length()));
            return formatPlan(monthlyCycleService.markInvested(
                new MarkInvestedRequest(amount, LocalDate.now(), "Marked invested from Telegram")));
        }
        if (trimmed.equals("/skip")) {
            return formatPlan(monthlyCycleService.skipCurrentCycle("Skipped from Telegram"));
        }
        if (trimmed.startsWith("/updatevalue ")) {
            BigDecimal corpus = parseAmount(trimmed.substring("/updatevalue ".length()));
            return formatSettings(settingsService.updateCorpusValue(new UpdateCorpusValueRequest(corpus)));
        }
        return "Unknown command.\n\n" + helpText();
    }

    private BigDecimal parseAmount(String value) {
        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Amount must be a valid number.");
        }
    }

    private String helpText() {
        return """
            <b>ClearGoal commands</b>

            /plan
            /progress
            /settings
            /amount 65000
            /invested
            /invested 60000
            /updatevalue 1245000
            /skip
            """;
    }

    private String formatPlan(MonthlyCycleResponse cycle) {
        String monthLabel = formatMonth(cycle.cycleMonth());
        return """
            <b>ClearGoal</b>
            <b>Current Plan</b>

            <b>Cycle</b>
            • Month: %s
            • Salary date: %s
            • Status: %s

            <b>Amounts</b>
            • Expected: ₹%s
            • Available: ₹%s
            • Invested: ₹%s

            <b>Notes</b>
            %s
            """.formatted(
            monthLabel,
            cycle.salaryDate(),
            cycle.status(),
            cycle.expectedAmount(),
            cycle.availableAmount(),
            cycle.actualInvestedAmount() == null ? "-" : cycle.actualInvestedAmount(),
            cycle.notes() == null ? "-" : escape(cycle.notes()));
    }

    private String formatProgress(GoalProgressResponse progress) {
        return """
            <b>ClearGoal</b>
            <b>Goal Progress</b>

            <b>Corpus</b>
            • Goal: ₹%s
            • Current: ₹%s
            • Invested: ₹%s
            • Gains: ₹%s

            <b>Progress</b>
            • Completion: %s%%
            • Remaining: ₹%s
            • Months to goal: %s
            """.formatted(
            progress.goalAmount(),
            progress.currentCorpus(),
            progress.totalInvestedCapital(),
            progress.estimatedGains(),
            progress.completionPercentage(),
            progress.remainingAmount(),
            progress.projectedMonthsToGoal());
    }

    private String formatSettings(SettingsResponse settings) {
        return """
            <b>ClearGoal</b>
            <b>Settings</b>

            <b>Money</b>
            • Goal: ₹%s
            • Current corpus: ₹%s
            • Total invested: ₹%s
            • Monthly amount: ₹%s

            <b>Profile</b>
            • Salary day: %s
            • Risk: %s
            • Time zone: %s
            • Chat ID: %s
            """.formatted(
            settings.goalAmount(),
            settings.currentCorpus(),
            settings.totalInvestedCapital(),
            settings.defaultMonthlyAmount(),
            settings.salaryDay(),
            settings.riskPreference(),
            settings.timeZone(),
            settings.telegramChatId() == null ? "-" : settings.telegramChatId());
    }

    private String formatMonth(String cycleMonth) {
        try {
            YearMonth month = YearMonth.parse(cycleMonth);
            return month.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + month.getYear();
        } catch (RuntimeException exception) {
            return cycleMonth;
        }
    }

    private String escape(String value) {
        return value
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;");
    }
}
