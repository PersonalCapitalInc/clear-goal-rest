# MVP Scope

## MVP objective

Build the smallest reliable version of ClearGoal that creates value every salary cycle.

The MVP is successful when it can run this loop without manual intervention:

```text
Prepare research → Generate recommendation → Send salary-day alert
→ Record completion → Update ₹1 crore progress → Create next cycle
```

## Must-have capabilities

### 1. Personal settings

Store the minimum configuration required to generate the monthly plan:

- Goal amount, default ₹1,00,00,000
- Current corpus
- Total invested capital
- Salary date, initially configurable with 30th as the default
- Default monthly investible amount
- Annual investment step-up percentage
- Base asset allocation
- Risk preference
- Telegram bot token and chat ID
- Time zone

### 2. Personal investment watchlist

ClearGoal should recommend only from a small list that I have already approved.

Suggested limits:

- Core equity funds: maximum 2
- Mid-cap funds: maximum 1
- Small-cap funds: maximum 1
- Debt or liquid funds: maximum 2
- Government options: selected T-Bills, G-Secs, PPF, FRSB, or similar
- Gold: optional

Each watchlist item stores:

- Instrument name
- Instrument type
- Allocation bucket
- Scheme or security identifier
- Active/inactive status
- Maximum allowed allocation
- Notes

### 3. Monthly market research

Before salary day, ClearGoal creates one research report using:

- Broad-market valuation
- Drawdown from recent high
- Medium-term momentum
- Market volatility
- Government-security yields
- RBI policy updates
- SEBI or regulatory updates
- Mutual-fund NAV and performance checks
- A limited set of high-quality financial news

The report classifies the market as:

- Attractive
- Fair
- Slightly expensive
- Expensive
- Highly volatile

The report must include:

- Observation
- Supporting metric
- Source
- Source publication date
- Data retrieval time
- Confidence level

### 4. Strategy recommendation

The strategy engine combines:

| Signal | Initial weight |
|---|---:|
| Current portfolio allocation gap | 35% |
| Market valuation | 25% |
| Drawdown and momentum | 15% |
| Interest rates and bond yields | 10% |
| Fund-level quality checks | 10% |
| News sentiment | 5% |

Rules:

- The base allocation remains the long-term anchor.
- Tactical changes are capped at ±10 percentage points.
- News can influence at most ±2 percentage points.
- Existing holdings are not sold automatically.
- Fresh money is directed towards underweight buckets.
- The system cannot add a new fund automatically.
- The recommendation must be fully explainable.

### 5. Salary-day Telegram alert

The main alert should include:

```text
CLEAR GOAL — MONTHLY INVESTMENT PLAN

Available amount: ₹65,000
Current corpus: ₹12,45,000
Goal progress: 12.45%
Market regime: FAIR
Confidence: MEDIUM

Suggested fresh-money allocation:
- Core equity: ₹35,000
- Mid-cap: ₹10,000
- Government/debt: ₹15,000
- Reserve: ₹5,000

Why:
- Core equity is below target.
- Mid-cap valuation remains elevated.
- Bond yields support the debt allocation.
- No thesis-changing news was identified.

Expected goal date before investment: March 2034
Expected goal date after investment: February 2034
```

Required actions:

- Mark invested
- Change monthly amount
- View research
- Skip month

### 6. Investment confirmation

The user can record:

- Actual amount invested
- Investment date
- Instruments used
- Amount per instrument
- Notes
- Completed, partially completed, or skipped status

### 7. ₹1 crore progress

Track only the metrics required for the goal:

- Current corpus
- Total invested capital
- Estimated gains
- Percentage of goal completed
- Remaining amount
- Expected goal date
- Contribution consistency
- Change in target date caused by the latest contribution

The corpus can initially be updated manually once per month from an existing investment application.

### 8. Minimal control panel

Only three screens are required:

1. **Settings and watchlist**
2. **Latest research and recommendation**
3. **Monthly history and goal progress**

## Telegram commands

```text
/plan                 Show this month’s recommendation
/research             Show the latest market-research summary
/amount 65000         Change the available amount for the current cycle
/invested             Mark the full recommendation as completed
/invested 60000       Record a different actual amount
/progress             Show progress towards ₹1 crore
/updatevalue 1245000  Update the current corpus
/skip                 Skip the current month
/settings             Show current configuration
```

## Out of scope for MVP

- Public sign-up
- Multiple users
- KYC
- Investment order execution
- Bank-account aggregation
- UPI or SIP mandates
- Daily portfolio tracking
- Daily market alerts
- Detailed transaction accounting
- Tax calculations
- Capital-gains reports
- CAS or PDF parsing
- Native mobile application
- Complex dashboards
- Chatbot-based investment decisions
- Automatic selling
- Kafka, Kubernetes, microservices, Redis, or Elasticsearch

## MVP definition of done

The MVP is complete when:

1. The scheduled research job succeeds before salary day.
2. A traceable recommendation is generated.
3. The salary-day Telegram alert arrives automatically.
4. The recommendation can be marked completed or skipped.
5. Goal progress updates correctly.
6. The next monthly cycle is created.
7. Job failures generate an operational alert.
