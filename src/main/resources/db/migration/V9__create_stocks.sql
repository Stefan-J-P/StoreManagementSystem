CREATE TABLE stocks
(
    id         BIGSERIAL NOT NULL,
    product_id BIGINT    NOT NULL,
    quantity   INTEGER   NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),

    CONSTRAINT pk_stocks PRIMARY KEY (id),
    CONSTRAINT uq_stocks_product_id UNIQUE (product_id),
    CONSTRAINT fk_stocks_product FOREIGN KEY (product_id) REFERENCES products (id)
);

