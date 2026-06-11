CREATE TABLE producers
(
    id         BIGSERIAL    NOT NULL,
    name       VARCHAR(150) NOT NULL,
    country_id BIGINT       NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at TIMESTAMP    NOT NULL DEFAULT now(),

    CONSTRAINT pk_producers PRIMARY KEY (id),
    CONSTRAINT fk_producers_country FOREIGN KEY (country_id) REFERENCES countries (id)
);

