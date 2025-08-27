# Parking Calculator Service

A Spring Boot microservice for calculating parking prices with different discount rules.

## Features

- Calculate parking prices based on hourly rates and discounts
- REST API with validation
- PostgresSQL persistence
- Testcontainers for integration testing

## Prerequisites

- Java 17
- Docker and Docker Compose
- Gradle

## Running the Application (Bash)

### Using Docker Compose (Recommended)

Build, Test & Run the project
```bash
# Build, Test & Run the project
./gradlew clean build
docker-compose up --build
```

### Using Gradle

```bash
# Start PostgresSQL first
docker-compose up postgres -d
# Run the application
./gradlew bootRun
```

## Testing

### Unit Tests

```bash
./gradlew test
```

### Integration Tests

```bash
./gradlew integrationTest
```

### Manual Testing

```bash
curl --location 'http://localhost:8080/tickets/calculate' \
--header 'Content-Type: application/json' \
--data '{
    "parkingId": "P000123",
    "from": "2025-08-25T00:00:00",
    "to": "2025-08-25T00:14:59"
}'
```

Or swagger-ui but the project should be built because it is a local url

[Parking Calculator Service Swagger UI](http://localhost:8080/swagger-ui/index.html)

## API Endpoints

- `POST /tickets/calculate` - Calculate parking price

## Parking Configurations

- **P000000**: €1/hour, No discount
- **P000123**: €2/hour, max €15/day
- **P000456**: €3/hour, max €20/12h, first hour free

## Project Structure

```
src/main/java/com/paymeter/parking_calculator/
├── application/
│   ├── commons/
│   ├── dto/
│   └── service/
├── domain/
│   ├── config/
│   ├── exception/
│   ├── model/
│   ├── port/
│   └── service/
└── infrastructure/
    ├── adapter/
    ├── controller/
    ├── entity/
    ├── interceptor/
    ├── repository/
    └── adapter/
```

## Built With

- Spring Boot 3.5.5
- PostgresSQL
- Lombok
- Testcontainers
- Gradle

## Documentation

### Add New Discount Type

[📄 ADD_NEW_DISCOUNT.md](./docs/ADD_NEW_DISCOUNT.md)
