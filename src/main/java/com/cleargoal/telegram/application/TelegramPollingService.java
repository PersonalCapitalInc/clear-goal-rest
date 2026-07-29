package com.cleargoal.telegram.application;

import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@ConditionalOnProperty(prefix = "cleargoal.telegram", name = "enabled", havingValue = "true")
public class TelegramPollingService {

    private static final Logger log = LoggerFactory.getLogger(TelegramPollingService.class);

    private final TelegramBotClient client;
    private final TelegramCommandService commandService;
    private final TelegramProperties properties;
    private final AtomicLong nextOffset = new AtomicLong(0);

    public TelegramPollingService(
        TelegramBotClient client,
        TelegramCommandService commandService,
        TelegramProperties properties
    ) {
        this.client = client;
        this.commandService = commandService;
        this.properties = properties;
    }

    @Scheduled(fixedDelayString = "${cleargoal.telegram.polling-delay:30000}")
    public void poll() {
        if (!StringUtils.hasText(properties.token())) {
            return;
        }
        for (TelegramBotClient.TelegramUpdate update : client.getUpdates(nextOffset.get())) {
            nextOffset.set(update.updateId() + 1);
            handle(update);
        }
    }

    private void handle(TelegramBotClient.TelegramUpdate update) {
        TelegramBotClient.TelegramMessage message = update.message();
        if (message == null || message.chat() == null || !StringUtils.hasText(message.text())) {
            return;
        }

        String chatId = String.valueOf(message.chat().id());
        if (StringUtils.hasText(properties.allowedChatId()) && !properties.allowedChatId().equals(chatId)) {
            log.warn("Ignoring Telegram message from unauthorized chat {}", chatId);
            return;
        }

        try {
            client.sendMessage(chatId, commandService.handleMessage(message.text()));
        } catch (RuntimeException exception) {
            log.warn("Telegram command failed", exception);
            client.sendMessage(chatId, "Command failed: " + exception.getMessage());
        }
    }
}
