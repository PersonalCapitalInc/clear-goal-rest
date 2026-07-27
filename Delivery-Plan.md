# Delivery Plan

## Delivery strategy

Build the product in the order in which it creates personal value:

```text
Alert → confirmation → goal progress → data ingestion → research → strategy → AI explanation
```

## Phase 1 — Functional salary alert

### Goal

Receive an automatic salary-day alert and record investment completion.

### Deliverables

- Spring Boot project
- PostgreSQL and Flyway
- Docker Compose
- Settings table
- Monthly cycle table
- Telegram bot restricted to one chat ID
- Salary-day scheduler
- Manually configured recommendation
- `/plan`, `/invested`, `/progress`, `/amount`, `/skip`
- Basic ₹1 crore target-date calculation

### Exit condition

```text
Scheduler fires → Telegram alert arrives → user marks invested
→ cycle updates → progress updates → next cycle is created
```

## Phase 2 — Research data foundation

### Goal

Generate a factual monthly research report with traceable sources.

### Deliverables

- AMFI NAV ingestion
- Index and valuation ingestion
- RBI and SEBI update ingestion
- Selected financial-news ingestion
- News deduplication
- Market snapshot
- Source freshness rules
- `/research` command

### Exit condition

The monthly report includes market condition, supporting metrics, source references, and data timestamps.

## Phase 3 — Strategy engine

### Goal

Convert the available amount into an explainable recommendation.

### Deliverables

- Base-allocation configuration
- Portfolio-gap calculation
- Market-regime score
- Tactical adjustment limits
- Watchlist filtering
- Recommendation items
- Goal-date impact
- Explanation template

### Exit condition

Every recommended amount is traceable to:

```text
Base allocation + portfolio gap + bounded tactical adjustment
```

## Phase 4 — Reliability and unattended operation

### Goal

Run the complete cycle without manual job execution.

### Deliverables

- Preview alert
- Final alert
- Follow-up reminders
- Transactional outbox
- Retry and idempotency controls
- Job failure alerts
- Backup script
- Health checks
- Minimal control panel

### Exit condition

A simulated monthly cycle runs end to end without duplicate alerts or manual restarts.

## Core epics

```text
CG-01  Personal Settings and Watchlist
CG-02  Salary-Day Scheduler and Telegram Alerts
CG-03  Market and News Research
CG-04  Monthly Strategy and Recommendation Engine
CG-05  Investment Confirmation and Goal Progress
CG-06  Deployment, Reliability and Backups
```

## First implementation milestone

Use fixed values first:

```text
Goal: ₹1,00,00,000
Current corpus: ₹5,00,000
Monthly amount: ₹65,000
Salary day: 30th
Allocation: manually configured
```

Do not begin automated market research until this vertical slice works completely.
