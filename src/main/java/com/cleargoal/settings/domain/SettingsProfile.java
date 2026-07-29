package com.cleargoal.settings.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
public class SettingsProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal goalAmount;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal currentCorpus;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal totalInvestedCapital;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal defaultMonthlyAmount;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal annualStepUpPercentage;

    @Column(nullable = false)
    private Integer salaryDay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private RiskPreference riskPreference;

    @Column(nullable = false, length = 64)
    private String timeZone;

    @Column(length = 64)
    private String telegramChatId;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(BigDecimal goalAmount) {
        this.goalAmount = goalAmount;
    }

    public BigDecimal getCurrentCorpus() {
        return currentCorpus;
    }

    public void setCurrentCorpus(BigDecimal currentCorpus) {
        this.currentCorpus = currentCorpus;
    }

    public BigDecimal getTotalInvestedCapital() {
        return totalInvestedCapital;
    }

    public void setTotalInvestedCapital(BigDecimal totalInvestedCapital) {
        this.totalInvestedCapital = totalInvestedCapital;
    }

    public BigDecimal getDefaultMonthlyAmount() {
        return defaultMonthlyAmount;
    }

    public void setDefaultMonthlyAmount(BigDecimal defaultMonthlyAmount) {
        this.defaultMonthlyAmount = defaultMonthlyAmount;
    }

    public BigDecimal getAnnualStepUpPercentage() {
        return annualStepUpPercentage;
    }

    public void setAnnualStepUpPercentage(BigDecimal annualStepUpPercentage) {
        this.annualStepUpPercentage = annualStepUpPercentage;
    }

    public Integer getSalaryDay() {
        return salaryDay;
    }

    public void setSalaryDay(Integer salaryDay) {
        this.salaryDay = salaryDay;
    }

    public RiskPreference getRiskPreference() {
        return riskPreference;
    }

    public void setRiskPreference(RiskPreference riskPreference) {
        this.riskPreference = riskPreference;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(String telegramChatId) {
        this.telegramChatId = telegramChatId;
    }
}
