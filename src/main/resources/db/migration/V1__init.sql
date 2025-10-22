-- Using VARCHAR status to avoid PG enum hassles
CREATE TABLE transactions (
  id UUID PRIMARY KEY,
  nip_ref VARCHAR(64) UNIQUE NOT NULL,
  account_id VARCHAR(64) NOT NULL,
  amount_kobo BIGINT NOT NULL,
  status VARCHAR(16) NOT NULL,          -- RECEIVED|PROVISIONAL|POSTED|REVERSED|FAILED
  reason VARCHAR(128),
  created_at TIMESTAMPTZ NOT NULL,
  updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE ledger (
  id BIGSERIAL PRIMARY KEY,
  tx_id UUID NOT NULL,
  account_id VARCHAR(64) NOT NULL,
  amount_kobo BIGINT NOT NULL,          -- +credit, -debit
  balance_after_kobo BIGINT NOT NULL,
  type VARCHAR(16) NOT NULL,            -- CREDIT|REVERSAL
  created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE provisional (
  tx_id UUID PRIMARY KEY,
  expires_at TIMESTAMPTZ NOT NULL,
  risk_score NUMERIC(5,3) NOT NULL
);

CREATE INDEX idx_ledger_account ON ledger(account_id);
CREATE INDEX idx_prov_expires ON provisional(expires_at);
