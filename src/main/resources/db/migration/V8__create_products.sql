CREATE TABLE products
(
    id          BIGSERIAL      NOT NULL,
    name        VARCHAR(200)   NOT NULL,
    price       NUMERIC(12, 2) NOT NULL,
    category_id BIGINT         NOT NULL,
    trade_id    BIGINT         NOT NULL,
    producer_id BIGINT         NOT NULL,
    created_at  TIMESTAMP      NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP      NOT NULL DEFAULT now(),

    CONSTRAINT pk_products PRIMARY KEY (id),
    CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT fk_products_trade FOREIGN KEY (trade_id) REFERENCES trades (id),
    CONSTRAINT fk_products_producer FOREIGN KEY (producer_id) REFERENCES producers (id)
);

