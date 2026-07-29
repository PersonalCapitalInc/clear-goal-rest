package com.cleargoal.monthlycycle.domain;

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
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
public class MonthlyCycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 7)
    private String cycleMonth;

    @Column(nullable = false)
    private LocalDate salaryDate;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal expectedAmount;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal availableAmount;

    @Column(precision = 19, scale = 2)
    private BigDecimal actualInvestedAmount;

    private LocalDate investedOn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private CycleStatus status;

    @Column(length = 500)
    private String notes;

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

    public String getCycleMonth() {
        return cycleMonth;
    }

    public void setCycleMonth(String cycleMonth) {
        this.cycleMonth = cycleMonth;
    }

    public LocalDate getSalaryDate() {
        return salaryDate;
    }

    public void setSalaryDate(LocalDate salaryDate) {
        this.salaryDate = salaryDate;
    }

    public BigDecimal getExpectedAmount() {
        return expectedAmount;
    }

    public void setExpectedAmount(BigDecimal expectedAmount) {
        this.expectedAmount = expectedAmount;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public BigDecimal getActualInvestedAmount() {
        return actualInvestedAmount;
    }

    public void setActualInvestedAmount(BigDecimal actualInvestedAmount) {
        this.actualInvestedAmount = actualInvestedAmount;
    }

    public LocalDate getInvestedOn() {
        return investedOn;
    }

    public void setInvestedOn(LocalDate investedOn) {
        this.investedOn = investedOn;
    }

    public CycleStatus getStatus() {
        return status;
    }

    public void setStatus(CycleStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
