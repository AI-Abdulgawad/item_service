# Item Service

The **data-owning microservice** in a small Spring Cloud system: it persists and serves `Dog` records from its own database, and registers itself with Eureka so other services can discover and call it without a hardcoded URL.

## Overview

This service represents a typical "downstream" microservice: it owns a single resource (dogs), persists it via Spring Data JPA/H2, and exposes it over HTTP. It doesn't call any other service — it registers with the Eureka discovery server on startup so that upstream consumers (like `main-service`) can find and call it by logical name (`item-service`) instead of a fixed host/port.

## Role in the System

This is one of three microservices that make up a single application:

| Service | Role | Port |
|---|---|---|
| [eureka-server](https://github.com/AI-Abdulgawad/eureka-server) | Service discovery registry | `8761` |
| **item-service** *(this repo)* | Owns and serves dog data (JPA + H2) | `8080` |
| [main-service](https://github.com/AI-Abdulgawad/main_service) | Public-facing aggregator, calls item-service via Eureka/Feign | `8081` |

```
                ┌────────────────────┐
                │   Eureka Server     │  (port 8761)
                └─────────▲──────────┘
                          │ register / discover
              ┌───────────┴────────────┐
              │                        │
     ┌────────┴────────┐     ┌─────────┴─────────┐
     │  item-service    │     │   main-service     │
     │  (this repo)     │◄────┤   (port 8081)      │
     │  port 8080       │Feign│   calls item-service│
     │  owns Dog data   │     │                     │
     └──────────────────┘     └────────────────────┘
                                        ▲
                                        │
                                   Browser / client
```

**Start [eureka-server](https://github.com/AI-Abdulgawad/eureka-server) first**, then this service, then [main-service](https://github.com/AI-Abdulgawad/main_service).

## Features

- **Owns the `Dog` resource**: persistence via Spring Data JPA, backed by an H2 in-memory database
- **Registers with Eureka** as `item-service`, discoverable by other services in the system
- **REST endpoint** to fetch all dogs (`/getAllDogs`)
- **springdoc-openapi/Swagger** included for interactive API documentation
- Configured for **Spring Cloud OpenFeign** and Spring Cloud Config client (available for future inter-service calls / centralized config)

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5.3, Spring Cloud 2025.0.0 |
| Discovery | Netflix Eureka Client |
| Persistence | Spring Data JPA, H2 (in-memory) |
| API Docs | springdoc-openapi (Swagger UI) |
| Tooling | Lombok |
| Build | Maven (with Maven Wrapper) |

## Getting Started

### Prerequisites
- Java 17+
- [eureka-server](https://github.com/AI-Abdulgawad/eureka-server) running on `localhost:8761` (this service will register with it on startup)
- No local Maven install required (Maven Wrapper included)

### Run locally

```bash
git clone https://github.com/AI-Abdulgawad/item_service.git
cd item_service
./mvnw spring-boot:run
```

The app starts on `http://localhost:8080` and registers itself with Eureka under the name `item-service`.

- **List all dogs**: `GET http://localhost:8080/getAllDogs`
- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
- **H2 console**: `http://localhost:8080/h2` (JDBC URL: `jdbc:h2:mem:/testdb`)

## Project Structure

```
src/main/java/com/udacity/item_service/
├── ItemServiceApplication.java     # @EnableDiscoveryClient entry point
├── controller/
│   ├── MainController.java         # GET /getAllDogs
│   └── ItemController.java         # Additional endpoint interface (/dogs)
├── repo/
│   └── DogRepo.java                # Spring Data JPA repository
└── model/
    └── Dog.java                    # JPA entity

src/main/resources/
├── data.sql                        # Seed data loaded on startup
└── application.properties
```

## What This Project Demonstrates

- Registering a Spring Boot service with a Eureka discovery server (`@EnableDiscoveryClient`)
- Exposing a simple JPA-backed REST resource for consumption by other services
- Structuring a microservice to be independently runnable and independently deployable
- Documenting the API with springdoc-openapi

## Related Services

- [eureka-server](https://github.com/AI-Abdulgawad/eureka-server) — the discovery registry this service registers with
- [main-service](https://github.com/AI-Abdulgawad/main_service) — discovers this service via Eureka and calls it through a Feign client to aggregate/display dog data

## Possible Extensions

- Add `POST`/`PUT`/`DELETE` endpoints for full CRUD on dogs
- Replace H2 with a persistent, service-owned database
- Secure the service-to-service calls (e.g. mutual TLS or a shared API key) rather than leaving `/getAllDogs` open

---

*This is a demo project (originally built as part of a Udacity course) intended to showcase Spring Cloud microservice fundamentals.*
