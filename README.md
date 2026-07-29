# ClearGoal

ClearGoal is a personal salary-day investment planner. This repository now contains:

- A Spring Boot starter application for the Phase 1 vertical slice
- PostgreSQL/Flyway local development setup
- The original product and architecture notes in Markdown

## Local run

Start PostgreSQL:

```bash
docker compose up -d
```

Start the application:

```bash
./mvnw spring-boot:run
```

Or run without Docker using the dev profile:

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

The app will bootstrap one default settings profile and one current monthly cycle on first run.

## Useful endpoints

- `GET /actuator/health`
- `GET /api/settings`
- `PATCH /api/settings/corpus`
- `PUT /api/settings`
- `GET /api/monthly-cycles/current`
- `PATCH /api/monthly-cycles/current/amount`
- `POST /api/monthly-cycles/current/mark-invested`
- `POST /api/monthly-cycles/current/skip`
- `GET /api/progress`
- `POST /api/telegram/test-message`

## Swagger

After the app starts, Swagger UI is available at `http://localhost:8080/swagger-ui.html`.

The OpenAPI JSON is available at `http://localhost:8080/v3/api-docs`.

## Telegram bot integration

Enable the Telegram bot by setting:

```bash
export CLEARGOAL_TELEGRAM_TOKEN=your_bot_token
export CLEARGOAL_TELEGRAM_CHAT_ID=your_chat_id
```

The app uses long polling and supports these commands:

- `/plan`
- `/progress`
- `/settings`
- `/amount 65000`
- `/invested`
- `/invested 60000`
- `/updatevalue 1245000`
- `/skip`

## Included docs

- `Home.md`
- `Architecture.md`
- `MVP.md`
- `Core-Flows.md`
- `Delivery-Plan.md`
