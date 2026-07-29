package com.cleargoal.settings.application;

import com.cleargoal.settings.api.SettingsResponse;
import com.cleargoal.settings.api.UpdateCorpusValueRequest;
import com.cleargoal.settings.api.UpdateSettingsRequest;
import com.cleargoal.settings.domain.SettingsProfile;
import com.cleargoal.settings.infrastructure.SettingsProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SettingsService {

    private final SettingsProfileRepository repository;

    public SettingsService(SettingsProfileRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public SettingsResponse getSettings() {
        return toResponse(loadSettings());
    }

    public SettingsResponse updateSettings(UpdateSettingsRequest request) {
        SettingsProfile profile = loadSettings();
        profile.setGoalAmount(request.goalAmount());
        profile.setCurrentCorpus(request.currentCorpus());
        profile.setTotalInvestedCapital(request.totalInvestedCapital());
        profile.setDefaultMonthlyAmount(request.defaultMonthlyAmount());
        profile.setAnnualStepUpPercentage(request.annualStepUpPercentage());
        profile.setSalaryDay(request.salaryDay());
        profile.setRiskPreference(request.riskPreference());
        profile.setTimeZone(request.timeZone());
        profile.setTelegramChatId(request.telegramChatId());
        return toResponse(repository.save(profile));
    }

    public SettingsResponse updateCorpusValue(UpdateCorpusValueRequest request) {
        SettingsProfile profile = loadSettings();
        profile.setCurrentCorpus(request.currentCorpus());
        return toResponse(repository.save(profile));
    }

    @Transactional(readOnly = true)
    public SettingsProfile loadSettings() {
        return repository.findAll().stream()
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException("Settings profile has not been created yet."));
    }

    public SettingsResponse toResponse(SettingsProfile profile) {
        return new SettingsResponse(
            profile.getId(),
            profile.getGoalAmount(),
            profile.getCurrentCorpus(),
            profile.getTotalInvestedCapital(),
            profile.getDefaultMonthlyAmount(),
            profile.getAnnualStepUpPercentage(),
            profile.getSalaryDay(),
            profile.getRiskPreference(),
            profile.getTimeZone(),
            profile.getTelegramChatId()
        );
    }
}
