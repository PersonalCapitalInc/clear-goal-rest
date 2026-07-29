package com.cleargoal.shared.api;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    OpenAPI clearGoalOpenApi() {
        return new OpenAPI().info(new Info()
            .title("ClearGoal API")
            .description("Private salary-day investment planning API with Telegram bot support.")
            .version("0.0.1")
            .contact(new Contact().name("ClearGoal")));
    }
}
