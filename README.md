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

## 1) Start the local stack

```bash
docker compose up -d
```

Open Keycloak:

http://localhost:8081

Default admin credentials:

```text
username: admin
password: admin
```

## 2) Create the realm

Create a new realm named:

```text
store-management
```

## 3) Create the client

Create a client with these settings:

```text
Client ID: store-api
Client Type: OpenID Connect
Access Type: Confidential
Standard Flow: Enabled
Direct Access Grants: Enabled
Service Accounts: Disabled
Valid Redirect URIs: http://localhost:8080/*
Web Origins: *
```

After creating the client, copy the client secret from the Credentials tab. You will need it for the password-grant token request.

## 4) Create the test user

Create a user with these values:

```text
Username: store-user
Password: store-password
Email: store@example.com
Enabled: true
```

## 5) Configure Spring Boot

The application validates JWTs using the Keycloak issuer URI:

```text
http://localhost:8081/realms/store-management
```

If needed, override it with an environment variable:

```bash
export KEYCLOAK_ISSUER_URI=http://localhost:8081/realms/store-management
```

## 6) Start the Spring Boot application

```bash
mvn spring-boot:run
```

## 7) Obtain an access token from Keycloak

Request a token with `curl`.

> Note: because the client is confidential, include the client secret from Keycloak.

```bash
curl -X POST \
  http://localhost:8081/realms/store-management/protocol/openid-connect/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "client_id=store-api" \
  -d "client_secret=<client-secret>" \
  -d "username=store-user" \
  -d "password=store-password" \
  -d "grant_type=password"
```

Store the token returned in the `access_token` field:

```text
TOKEN=<access_token>
```

## 8) Call a secured endpoint

```bash
curl -H "Authorization: Bearer $TOKEN" \
  http://localhost:8080/api/v1/products
```

## 9) Quick verification

- `GET /actuator/health` should be accessible without a token.
- `GET /api/v1/products` should require a valid JWT.
- `GET /api/v1/orders` should require a valid JWT.

Keycloak is exposed locally for OAuth2 / OIDC development, while PostgreSQL and RabbitMQ remain available with their existing configuration.

