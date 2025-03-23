# Weather Backend

This is a Spring Boot-based backend application that fetches current weather data from the OpenWeatherMap API for a list
of cities, stores the data in a PostgreSQL database, and provides a REST API for retrieving the weather information. The
application also uses Flyway for database migrations, Spring Retry for resilient API calls, and scheduled tasks to
update weather data daily.


## Features

- **Fetch Weather Data:**  
  Retrieves weather information from OpenWeatherMap using city location (using IDs is deprecated).
- **Database Integration:**  
  Stores current weather data in a PostgreSQL database.
- **Scheduled Updates:**  
  Uses Spring's scheduling support to update weather data daily.
- **Flyway Migrations:**  
  Manages database schema changes with Flyway.
- **Global Exception Handling:**  
  Provides a centralized error handling mechanism via `@ControllerAdvice`.
- **API Endpoints:**  
  Exposes a REST API for frontend consumption (pagination and sorting supported).

## Technology Stack

- **Java 11**
- **Spring Boot 2.7.9**
- **Gradle**
- **PostgreSQL**
- **Flyway**
- **Spring Retry**
- **Spring Scheduling**
- **Docker & Docker Compose**

## Getting Started

### Prerequisites

- **Java 11** or later
- **Gradle** (or use the Gradle wrapper provided)
- **PostgreSQL** (or use the provided Docker Compose setup)
- **Docker** (optional, for containerized deployment)

### Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/your-username/weather-backend.git
   cd weather-backend
2. **Configure Environment Variables:**

   **API Key**
    - The API key for the OpenWeatherMap API is read from the environment variable WEATHER_API_KEY. If not set, it
      defaults to MY_WEATHER_API_KEY:
    - weather.api.key=${WEATHER_API_KEY:MY_WEATHER_API_KEY}

3. **Database Setup:**

   - Ensure you have a PostgreSQL instance running. Alternatively, use Docker Compose

4. **Flyway Migrations**

   - Flyway is configured to look for migration scripts in classpath:db/migration. The initial migration script,
     V1__Create_weather_table.sql, creates the weather table

5. **Testing**

- Unit tests are provided (located under src/test/java). To run tests:
