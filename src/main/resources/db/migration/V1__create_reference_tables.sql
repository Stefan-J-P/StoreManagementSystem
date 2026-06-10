-- ═══════════════════════════════════════════════════════════════════
-- V1__create_reference_tables.sql
--
-- Phase 1: Reference Data Foundation
-- Entities: Country, Category, Trade, Payment
--
-- Dependencies: None – these tables have no foreign keys.
-- Required by: V2 (Customer, Producer, Shop will reference countries and trades)
--
-- Design notes:
--   - All PKs use BIGSERIAL (auto-increment BIGINT) mapped to Long in Java
--   - All business-key columns carry a UNIQUE constraint
--   - VARCHAR lengths reflect domain constraints, not arbitrary defaults
--   - audit columns (created_at, updated_at) included from the start;
--     populated by Hibernate @CreationTimestamp / @UpdateTimestamp or
--     Spring Data @EnableJpaAuditing
-- ═══════════════════════════════════════════════════════════════════

-- ─── countries ───────────────────────────────────────────────────
CREATE TABLE countries
(
    id         BIGSERIAL    NOT NULL,
    name       VARCHAR(100) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at TIMESTAMP    NOT NULL DEFAULT now(),

    CONSTRAINT pk_countries PRIMARY KEY (id),
    CONSTRAINT uq_countries_name UNIQUE (name)
);

COMMENT ON TABLE  countries      IS 'Reference: ISO country names used by customers, shops, and producers.';
COMMENT ON COLUMN countries.name IS 'Full country name; must be unique across the table.';

-- ─── categories ──────────────────────────────────────────────────
CREATE TABLE categories
(
    id         BIGSERIAL    NOT NULL,
    name       VARCHAR(100) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at TIMESTAMP    NOT NULL DEFAULT now(),

    CONSTRAINT pk_categories PRIMARY KEY (id),
    CONSTRAINT uq_categories_name UNIQUE (name)
);

COMMENT ON TABLE  categories      IS 'Reference: product categories (e.g. Electronics, Furniture).';
COMMENT ON COLUMN categories.name IS 'Category label; must be unique.';

-- ─── trades ──────────────────────────────────────────────────────
CREATE TABLE trades
(
    id          BIGSERIAL    NOT NULL,
    trade_name  VARCHAR(100) NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT now(),

    CONSTRAINT pk_trades PRIMARY KEY (id),
    CONSTRAINT uq_trades_trade_name UNIQUE (trade_name)
);

COMMENT ON TABLE  trades            IS 'Reference: trade / industry classification used by producers and products.';
COMMENT ON COLUMN trades.trade_name IS 'Trade label; must be unique.';

-- ─── payments ────────────────────────────────────────────────────
CREATE TABLE payments
(
    id           BIGSERIAL   NOT NULL,
    payment_type VARCHAR(50) NOT NULL,
    created_at   TIMESTAMP   NOT NULL DEFAULT now(),
    updated_at   TIMESTAMP   NOT NULL DEFAULT now(),

    CONSTRAINT pk_payments PRIMARY KEY (id),
    CONSTRAINT uq_payments_payment_type UNIQUE (payment_type)
);

COMMENT ON TABLE  payments             IS 'Reference: allowed payment methods for orders (e.g. CASH, CARD, TRANSFER).';
COMMENT ON COLUMN payments.payment_type IS 'Payment method label; maps to EPayment enum in the legacy model.';

