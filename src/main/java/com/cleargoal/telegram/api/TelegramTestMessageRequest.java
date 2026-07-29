package com.cleargoal.telegram.api;

import jakarta.validation.constraints.NotBlank;

public record TelegramTestMessageRequest(@NotBlank String text) {
}
