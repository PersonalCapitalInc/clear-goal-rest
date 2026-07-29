package com.cleargoal.telegram.application;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

@Component
@ConditionalOnProperty(prefix = "cleargoal.telegram", name = "enabled", havingValue = "true")
public class TelegramBotClient {

    private final RestClient restClient;

    public TelegramBotClient(TelegramProperties properties) {
        this.restClient = RestClient.builder()
            .baseUrl("https://api.telegram.org/bot" + properties.token())
            .build();
    }

    public List<TelegramUpdate> getUpdates(long offset) {
        TelegramResponse<List<TelegramUpdate>> response = restClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/getUpdates")
                .queryParam("offset", offset)
                .queryParam("timeout", 0)
                .build())
            .retrieve()
            .body(new ParameterizedTypeReference<>() {
            });
        if (response == null || !response.ok() || response.result() == null) {
            return List.of();
        }
        return response.result();
    }

    public void sendMessage(String chatId, String text) {
        if (!StringUtils.hasText(chatId) || !StringUtils.hasText(text)) {
            return;
        }
        restClient.post()
            .uri("/sendMessage")
            .contentType(MediaType.APPLICATION_JSON)
            .body(new SendMessageRequest(chatId, text, "HTML"))
            .retrieve()
            .toBodilessEntity();
    }

    private record SendMessageRequest(
        @JsonProperty("chat_id") String chatId,
        String text,
        @JsonProperty("parse_mode") String parseMode
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TelegramResponse<T>(boolean ok, T result) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TelegramUpdate(
        @JsonProperty("update_id") long updateId,
        TelegramMessage message
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TelegramMessage(
        long message_id,
        TelegramChat chat,
        String text
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TelegramChat(Long id) {
    }
}
