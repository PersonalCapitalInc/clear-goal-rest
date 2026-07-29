package com.cleargoal.goal.api;

import com.cleargoal.goal.application.GoalProgressService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/progress")
public class GoalProgressController {

    private final GoalProgressService service;

    public GoalProgressController(GoalProgressService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get current progress toward the corpus goal")
    public GoalProgressResponse getProgress() {
        return service.getProgress();
    }
}
