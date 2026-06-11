-- Seed a minimal set of categories used for manual API checks.
INSERT INTO categories (name)
VALUES ('Electronics'),
       ('Furniture'),
       ('Tools'),
       ('Sport'),
       ('Machines')
ON CONFLICT (name) DO NOTHING;

