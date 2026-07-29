package com.cleargoal.monthlycycle.api;

import com.cleargoal.monthlycycle.application.MonthlyCycleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/monthly-cycles/current")
public class MonthlyCycleController {

    private final MonthlyCycleService service;

    public MonthlyCycleController(MonthlyCycleService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get the current monthly investment cycle")
    public MonthlyCycleResponse getCurrentCycle() {
        return service.getCurrentCycle();
    }

    @PatchMapping("/amount")
    @Operation(summary = "Change the available amount for the current cycle")
    public MonthlyCycleResponse updateAvailableAmount(@Valid @RequestBody UpdateCycleAmountRequest request) {
        return service.updateAvailableAmount(request);
    }

    @PostMapping("/mark-invested")
    @Operation(summary = "Mark the current cycle as invested")
    public MonthlyCycleResponse markInvested(@Valid @RequestBody MarkInvestedRequest request) {
        return service.markInvested(request);
    }

    @PostMapping("/skip")
    @Operation(summary = "Skip the current monthly cycle")
    public MonthlyCycleResponse skipCurrentCycle(@RequestParam(required = false) String notes) {
        return service.skipCurrentCycle(notes);
    }
}
