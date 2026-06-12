-- V13__seed_order_items.sql
--
-- Depends on:
--   V2  - countries     (country_id=1  = Poland)
--   V3  - categories    (category_id=1 = Electronics)
--   V4  - trades        (trade_id=1    = Electronics)
--   V5  - payments      (payment_id=1  = Cash)
--   V11 - orders        (order_id 1-3 seeded)
--
-- Seeds one sample producer and one sample product so that order_items
-- can reference them without requiring manual POST requests.

-- ─── Sample producer (country_id=1 = Poland) ─────────────────────
INSERT INTO producers (name, country_id)
VALUES ('Samsung Electronics', 1);

-- ─── Sample product (category=Electronics, trade=Electronics, producer=1) ──
INSERT INTO products (name, price, category_id, trade_id, producer_id)
VALUES ('Galaxy Laptop Pro', 3999.99, 1, 1, 1);

-- ─── Sample order items ───────────────────────────────────────────
-- order_id 1-3 seeded in V11, product_id=1 seeded above
INSERT INTO order_items (order_id, product_id, quantity, unit_price)
VALUES (1, 1, 2, 3999.99),
       (2, 1, 1, 3999.99),
       (3, 1, 3, 3999.99);

