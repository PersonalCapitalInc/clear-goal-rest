package com.cleargoal.settings.application;

import com.cleargoal.monthlycycle.domain.CycleStatus;
import com.cleargoal.monthlycycle.domain.MonthlyCycle;
import com.cleargoal.monthlycycle.infrastructure.MonthlyCycleRepository;
import com.cleargoal.settings.domain.RiskPreference;
import com.cleargoal.settings.domain.SettingsProfile;
import com.cleargoal.settings.infrastructure.SettingsProfileRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapDataInitializer implements CommandLineRunner {

    private final BootstrapProperties properties;
    private final SettingsProfileRepository settingsRepository;
    private final MonthlyCycleRepository cycleRepository;

    public BootstrapDataInitializer(
        BootstrapProperties properties,
        SettingsProfileRepository settingsRepository,
        MonthlyCycleRepository cycleRepository
    ) {
        this.properties = properties;
        this.settingsRepository = settingsRepository;
        this.cycleRepository = cycleRepository;
    }

    @Override
    public void run(String... args) {
        if (!properties.enabled()) {
            return;
        }

        SettingsProfile settings = settingsRepository.findAll().stream().findFirst().orElseGet(() -> {
            SettingsProfile profile = new SettingsProfile();
            profile.setGoalAmount(new BigDecimal("10000000.00"));
            profile.setCurrentCorpus(new BigDecimal("500000.00"));
            profile.setTotalInvestedCapital(new BigDecimal("450000.00"));
            profile.setDefaultMonthlyAmount(new BigDecimal("65000.00"));
            profile.setAnnualStepUpPercentage(new BigDecimal("10.00"));
            profile.setSalaryDay(30);
            profile.setRiskPreference(RiskPreference.MODERATE);
            profile.setTimeZone("Asia/Kolkata");
            return settingsRepository.save(profile);
        });

        cycleRepository.findTopByOrderByCycleMonthDesc().orElseGet(() -> {
            YearMonth currentMonth = YearMonth.now();
            MonthlyCycle cycle = new MonthlyCycle();
            cycle.setCycleMonth(currentMonth.toString());
            cycle.setSalaryDate(resolveSalaryDate(currentMonth, settings.getSalaryDay()));
            cycle.setExpectedAmount(settings.getDefaultMonthlyAmount());
            cycle.setAvailableAmount(settings.getDefaultMonthlyAmount());
            cycle.setStatus(CycleStatus.PLANNED);
            cycle.setNotes("Bootstrapped starter cycle");
            return cycleRepository.save(cycle);
        });
    }

    private LocalDate resolveSalaryDate(YearMonth month, int salaryDay) {
        return month.atDay(Math.min(salaryDay, month.lengthOfMonth()));
    }
}
