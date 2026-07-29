package com.cleargoal.telegram.api;

import com.cleargoal.telegram.application.TelegramBotClient;
import com.cleargoal.telegram.application.TelegramProperties;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/telegram")
@ConditionalOnProperty(prefix = "cleargoal.telegram", name = "enabled", havingValue = "true")
public class TelegramAdminController {

    private final TelegramBotClient client;
    private final TelegramProperties properties;

    public TelegramAdminController(TelegramBotClient client, TelegramProperties properties) {
        this.client = client;
        this.properties = properties;
    }

    @PostMapping("/test-message")
    @Operation(summary = "Send a test message to the configured Telegram chat")
    public ResponseEntity<Void> sendTestMessage(@Valid @RequestBody TelegramTestMessageRequest request) {
        if (!StringUtils.hasText(properties.allowedChatId())) {
            return ResponseEntity.badRequest().build();
        }
        client.sendMessage(properties.allowedChatId(), request.text());
        return ResponseEntity.accepted().build();
    }
}
