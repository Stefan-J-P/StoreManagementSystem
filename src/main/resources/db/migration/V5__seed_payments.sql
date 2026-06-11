-- Seed a minimal set of payments used for manual API checks.
INSERT INTO payments (payment_type)
VALUES ('Cash'),
       ('Card'),
       ('Transfer'),
       ('BLIK')
ON CONFLICT (payment_type) DO NOTHING;

