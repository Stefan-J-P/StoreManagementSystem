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

