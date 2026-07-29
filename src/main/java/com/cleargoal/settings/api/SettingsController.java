package com.cleargoal.settings.api;

import com.cleargoal.settings.application.SettingsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    private final SettingsService service;

    public SettingsController(SettingsService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get the active personal settings profile")
    public SettingsResponse getSettings() {
        return service.getSettings();
    }

    @PutMapping
    @Operation(summary = "Replace the active personal settings profile")
    public SettingsResponse updateSettings(@Valid @RequestBody UpdateSettingsRequest request) {
        return service.updateSettings(request);
    }

    @PatchMapping("/corpus")
    @Operation(summary = "Update only the current corpus value")
    public SettingsResponse updateCorpusValue(@Valid @RequestBody UpdateCorpusValueRequest request) {
        return service.updateCorpusValue(request);
    }
}
