# Store Management System

Spring Boot 3.5 learning project with PostgreSQL, Flyway and RabbitMQ.

# Local Development

1. Start infrastructure

```bash
docker compose up -d
```

2. Verify containers

```bash
docker ps
```

3. RabbitMQ UI

http://localhost:15672

guest / guest

4. Run application

```bash
mvn spring-boot:run
```

5. Stop infrastructure

```bash
docker compose down
```

# Keycloak Local Development Setup

1. Start the local infrastructure, including Keycloak

```bash
docker compose up -d
```

2. Open Keycloak in the browser

http://localhost:8081

3. Default admin credentials

```text
username: admin
password: admin
```

4. Start the Spring Boot application

```bash
mvn spring-boot:run
```

Keycloak is exposed locally for OAuth2 / OIDC development, while PostgreSQL and RabbitMQ remain available with their existing configuration.

