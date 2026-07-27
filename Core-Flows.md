# Core Flows

## 1. First-time setup

```mermaid
sequenceDiagram
    actor User
    participant Web as Private Control Panel
    participant App as ClearGoal
    participant DB as PostgreSQL
    participant TG as Telegram

    User->>Web: Enter goal, salary date, monthly amount, allocation
    User->>Web: Add approved watchlist instruments
    Web->>App: Save settings
    App->>DB: Persist configuration
    User->>TG: Send /settings
    TG->>App: Request configuration
    App->>TG: Return active settings
```

## 2. Research generation

```mermaid
sequenceDiagram
    participant S as Scheduler
    participant I as Data Ingestion
    participant D as External Data Sources
    participant R as Research Engine
    participant DB as PostgreSQL

    S->>I: Start monthly research job
    I->>D: Fetch market data and news
    D-->>I: Return source data
    I->>DB: Save data and timestamps
    I->>R: Start research generation
    R->>DB: Read market, portfolio, and news data
    R->>DB: Save market regime and research report
```

## 3. Recommendation generation

```mermaid
sequenceDiagram
    participant S as Scheduler
    participant E as Strategy Engine
    participant DB as PostgreSQL
    participant G as Goal Engine

    S->>E: Generate recommendation for monthly cycle
    E->>DB: Read available amount and base allocation
    E->>DB: Read latest portfolio and research report
    E->>E: Calculate portfolio gaps
    E->>E: Apply bounded tactical adjustments
    E->>E: Map allocation to approved instruments
    E->>G: Calculate goal-date impact
    G-->>E: Return before and after target dates
    E->>DB: Save recommendation and explanation
```

## 4. Salary-day alert

```mermaid
sequenceDiagram
    participant S as Scheduler
    participant O as Notification Outbox
    participant B as Telegram Bot
    actor User

    S->>O: Queue salary-day message
    O->>B: Deliver message
    B->>User: Show recommendation and actions
    User->>B: Select INVESTED
    B->>O: Record callback
    O->>O: Update cycle status
    B->>User: Confirm progress update
```

## 5. Manual corpus update

The MVP does not integrate with broker or bank accounts.

The user periodically copies the current corpus from an existing investment application:

```text
/updatevalue 1245000
```

ClearGoal stores a new portfolio snapshot and recalculates:

- Goal progress
- Remaining amount
- Estimated gains
- Current allocation gaps
- Expected target date

## 6. Failed data workflow

```mermaid
flowchart TD
    A[Scheduled ingestion starts] --> B{Critical data fetched?}
    B -- Yes --> C[Generate research]
    B -- No --> D[Retry with backoff]
    D --> E{Retry successful?}
    E -- Yes --> C
    E -- No --> F[Mark research failed]
    F --> G[Send operational Telegram alert]
    G --> H[Require manual review]
```

ClearGoal must not silently generate a recommendation from incomplete or stale data.
