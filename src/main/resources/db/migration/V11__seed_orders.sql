-- V11__seed_orders.sql
--
-- Seeds a sample customer (country_id=1 = Poland, seeded in V2) and
-- 3 sample orders referencing that customer and payment_id=1 (Cash, seeded in V5).
--
-- This migration is idempotent: the customer insert uses ON CONFLICT (email) DO NOTHING.
-- Orders have no unique business key so they are inserted unconditionally;
-- Flyway guarantees this script runs exactly once.

-- ─── Prerequisite: sample customer ───────────────────────────────
INSERT INTO customers (first_name, last_name, email, age, country_id)
VALUES ('Jan', 'Kowalski', 'jan.kowalski@example.com', 35, 1)
ON CONFLICT (email) DO NOTHING;

-- ─── Sample orders ────────────────────────────────────────────────
-- customer_id=1 (Jan Kowalski above), payment_id=1 (Cash from V5)
INSERT INTO orders (customer_id, payment_id, order_date, status)
VALUES (1, 1, '2026-01-15 10:30:00', 'NEW'),
       (1, 1, '2026-03-22 14:15:00', 'NEW'),
       (1, 1, '2026-06-01 09:00:00', 'NEW');

