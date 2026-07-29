package com.cleargoal.monthlycycle.application;

import com.cleargoal.monthlycycle.api.MarkInvestedRequest;
import com.cleargoal.monthlycycle.api.MonthlyCycleResponse;
import com.cleargoal.monthlycycle.api.UpdateCycleAmountRequest;
import com.cleargoal.monthlycycle.domain.CycleStatus;
import com.cleargoal.monthlycycle.domain.MonthlyCycle;
import com.cleargoal.monthlycycle.infrastructure.MonthlyCycleRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MonthlyCycleService {

    private final MonthlyCycleRepository repository;

    public MonthlyCycleService(MonthlyCycleRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public MonthlyCycleResponse getCurrentCycle() {
        return toResponse(loadCurrentCycle());
    }

    public MonthlyCycleResponse updateAvailableAmount(UpdateCycleAmountRequest request) {
        MonthlyCycle cycle = loadCurrentCycle();
        cycle.setAvailableAmount(request.availableAmount());
        return toResponse(repository.save(cycle));
    }

    public MonthlyCycleResponse markInvested(MarkInvestedRequest request) {
        MonthlyCycle cycle = loadCurrentCycle();
        cycle.setActualInvestedAmount(request.actualInvestedAmount());
        cycle.setInvestedOn(request.investedOn() == null ? LocalDate.now() : request.investedOn());
        cycle.setStatus(CycleStatus.INVESTED);
        cycle.setNotes(request.notes());
        return toResponse(repository.save(cycle));
    }

    public MonthlyCycleResponse skipCurrentCycle(String notes) {
        MonthlyCycle cycle = loadCurrentCycle();
        cycle.setStatus(CycleStatus.SKIPPED);
        cycle.setNotes(notes);
        return toResponse(repository.save(cycle));
    }

    @Transactional(readOnly = true)
    public MonthlyCycle loadCurrentCycle() {
        return repository.findTopByOrderByCycleMonthDesc()
            .orElseThrow(() -> new EntityNotFoundException("No monthly cycle exists yet."));
    }

    private MonthlyCycleResponse toResponse(MonthlyCycle cycle) {
        return new MonthlyCycleResponse(
            cycle.getId(),
            cycle.getCycleMonth(),
            cycle.getSalaryDate(),
            cycle.getExpectedAmount(),
            cycle.getAvailableAmount(),
            cycle.getActualInvestedAmount(),
            cycle.getInvestedOn(),
            cycle.getStatus(),
            cycle.getNotes()
        );
    }
}
