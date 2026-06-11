CREATE TABLE customers
(
    id         BIGSERIAL    NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    email      VARCHAR(150) NOT NULL,
    age        INTEGER      NOT NULL,
    country_id BIGINT       NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at TIMESTAMP    NOT NULL DEFAULT now(),

    CONSTRAINT pk_customers PRIMARY KEY (id),
    CONSTRAINT uq_customers_email UNIQUE (email),
    CONSTRAINT fk_customers_country FOREIGN KEY (country_id) REFERENCES countries (id)
);

