-- Seed a minimal set of countries used for manual API checks.
INSERT INTO countries (name)
VALUES ('Poland'),
       ('Germany'),
       ('France')
ON CONFLICT (name) DO NOTHING;

