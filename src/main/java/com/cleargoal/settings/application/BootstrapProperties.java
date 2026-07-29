package com.cleargoal.settings.application;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cleargoal.bootstrap")
public record BootstrapProperties(boolean enabled) {
}
