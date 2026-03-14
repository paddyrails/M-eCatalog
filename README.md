# M-eCatalog

## Introduction

M-eCatalog is the **CS Sales Catalog Service** for Apple CS Sales. It exposes a product catalog API so clients can retrieve products with country-specific pricing. The service is part of the broader CS Sales ecosystem and supports the United States (US), Canada (CAN), and Mexico (MEX). It is designed as a single-purpose catalog service that other sales applications (e.g. quotes, orders, offers) can consume to display or use product information and prices in the correct currency for each market.

---

## Business Architecture

- **Purpose:** Provide a single, consistent view of the sellable product catalog with correct pricing per country for CS Sales channels.
- **Capabilities:** Clients request the list of products for a given country and receive product identity (name, code, description) and the applicable price for that country.
- **Scope:** Catalog is read-only from the API perspective; product and price data are maintained elsewhere and served by this service.
- **Stakeholders:** Downstream sales applications (e.g. eQuotes, eOrders, eOffers) and any client that needs to show or use catalog data for US, Canada, or Mexico.

---

## Data Architecture

- **Core entity:** Product. Each product has a unique identifier, name, code, description, and separate list prices for US, Canada, and Mexico. The service stores these as country-specific price columns and returns only the relevant price for the requested country.
- **Data store:** Relational store (PostgreSQL). Products are stored in a single table keyed by product id, with columns for identity attributes and per-country prices.
- **Data flow:** Read path only: API request specifies country → service resolves the correct price column for that country → product list with name, code, description, and that country’s price is returned. No write APIs are exposed.
- **Caching:** Product catalog responses are cached in memory (e.g. Caffeine) to reduce database load and improve latency for repeated requests by country.

---

## Application Architecture

- **API layer:** REST over HTTP. Single catalog endpoint: get products by country (query parameter). Contract is defined in OpenAPI 3 (YAML in the repo) and used for server stub generation and documentation (e.g. Swagger UI).
- **Service layer:** Business logic that interprets the country parameter, calls the data access layer, maps persisted entities to response DTOs, and selects the correct price per product for the requested country. Caching is applied at or above this layer.
- **Data access layer:** Persistence of products (JPA) with a repository/DAO abstraction so the service is decoupled from the storage implementation.
- **Deployment unit:** Single deployable service (e.g. Spring Boot executable). No embedded UI beyond API documentation (Swagger UI). Configuration is externalized (e.g. database URL, credentials, cache settings) via configuration files or environment variables.

---

## Technology Architecture

- **Runtime:** Java 17.
- **Framework:** Spring Boot 3.2 (web, data JPA, cache, dev tools).
- **API:** REST; OpenAPI 3.0 for contract and documentation; SpringDoc used to serve Swagger UI from the same application.
- **Persistence:** JPA/Hibernate with PostgreSQL; connection pooling (e.g. HikariCP) and optional leak detection; schema managed by Hibernate (e.g. update strategy in non-production).
- **Caching:** Spring Cache abstraction with Caffeine as the provider; cache configuration (size, TTL) defined in application configuration.
- **Build:** Maven; multi-module layout with a parent POM and a service module that produces the runnable artifact.
- **Operations:** Service runs as a single process; database and cache are the main external dependencies. Database can be run via Docker for local development using the credentials and database name referenced in the application configuration.
