CREATE TABLE orders
(
    id          BIGSERIAL    NOT NULL,
    customer_id BIGINT       NOT NULL,
    payment_id  BIGINT       NOT NULL,
    order_date  TIMESTAMP    NOT NULL,
    status      VARCHAR(30)  NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT now(),

    CONSTRAINT pk_orders PRIMARY KEY (id),
    CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id) REFERENCES customers (id),
    CONSTRAINT fk_orders_payment FOREIGN KEY (payment_id) REFERENCES payments (id)
);

