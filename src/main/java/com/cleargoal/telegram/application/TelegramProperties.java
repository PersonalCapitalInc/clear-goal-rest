package com.cleargoal.telegram.application;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cleargoal.telegram")
public record TelegramProperties(
    boolean enabled,
    String token,
    String allowedChatId,
    Duration pollingDelay
) {
}
