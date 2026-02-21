# BlackRock Auto-Savings API

A scalable Spring Boot application that automates micro-savings using rule-based processing and simulates long-term investment returns.

Developed as part of the BlackRock Hackathon Challenge.

---

## 🚀 Features

- Transaction parsing with ceiling and remanent calculation
- Temporal rule processing (Q, P, K periods)
- Rule-based savings aggregation
- Investment return simulation (Index Funds and NPS)
- Inflation and tax benefit adjustment
- High-performance rule engine
- Dockerized deployment
- RESTful APIs

---

## 🛠 Tech Stack

- Java 17+
- Spring Boot
- Maven
- Docker
- REST APIs
- JUnit & Mockito (Testing)

---

## 📁 Project Structure
src/
└── main/
└── java/
└── com.himaja.blackrock.savings_api
├── controller
├── service
├── model
└── config


- controller → REST endpoints
- service → Business logic
- model → Domain objects

---

## ⚙️ Local Setup

### Prerequisites

- Java 17+
- Maven
- Docker (optional)

### Build

```bash
./mvnw clean package -DskipTests

Run Locally
./mvnw spring-boot:run

Server runs on:

http://localhost:5477

🐳 Docker Setup
Pull Image
docker pull himaja2904/blackrock-savings-api
Run Container
docker run -p 5477:5477 himaja2904/blackrock-savings-api

Application URL:

http://localhost:5477
🔗 API Endpoints

Base Path:

/blackrock/challenge/v1/transactions
1️⃣ Parse Transactions
POST /parse

Builds transactions from raw expenses.

2️⃣ Apply Rules
POST /filter

Applies Q (override), P (additive), and K (grouping) rules.

3️⃣ Index Returns
POST /returns/index

Calculates index fund investment returns.

4️⃣ NPS Returns
POST /returns/nps

Calculates NPS returns with tax benefits.

🧠 System Design
Architecture

Layered architecture

Stateless services

Separation of concerns

In-memory optimized processing

Rule Engine

Q Period → Overrides remanent

P Period → Adds extra savings

K Period → Aggregates by range

Returns Engine

Compound interest computation

Inflation-adjusted returns

Simplified tax calculation

⚡ Performance & Scalability

The rule engine was stress-tested using synthetic data.

Service-Level Benchmark
Records	Time
200k	~3s
1M	~6s

Controller-Level Benchmark

100k records (~7.5MB payload)

Response time: ~840ms

Optimization Techniques

Sorting and binary search

Sweep-line algorithm for additive rules

Prefix sum for aggregation

Efficient in-memory structures

🧪 Testing
Unit Tests

JUnit 5

Mockito for controller isolation

Load Tests

Service-level benchmarking

Controller integration testing

Run tests:
./mvnw test

📹 Demo
A 3–5 minute demonstration video is provided as part of the submission.

Author:
Himaja Mallepalli
Github: https://github.com/Himaja2904
Docker: https://hub.docker.com/r/himaja2904/blackrock-savings-api
