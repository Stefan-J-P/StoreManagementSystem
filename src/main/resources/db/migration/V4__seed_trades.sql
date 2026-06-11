-- Seed a minimal set of trades used for manual API checks.
INSERT INTO trades (trade_name)
VALUES ('Electronics'),
       ('Furniture'),
       ('Machines'),
       ('Tools'),
       ('Sport')
ON CONFLICT (trade_name) DO NOTHING;

