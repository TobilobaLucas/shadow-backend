package com.example.shadow.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "ledger")
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tx_id", nullable = false)
    private UUID txId;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(name = "amount_kobo", nullable = false)
    private long amountKobo;

    @Column(name = "balance_after_kobo", nullable = false)
    private long balanceAfterKobo;

    private String type; // CREDIT | REVERSAL

    @Column(name = "created_at")
    private Instant createdAt;

    @PrePersist
    void prePersist() {
        createdAt = Instant.now();
    }

    // --- Constructors ---

    public LedgerEntry() {}

    public LedgerEntry(UUID txId, String accountId, long amountKobo, long balanceAfterKobo, String type) {
        this.txId = txId;
        this.accountId = accountId;
        this.amountKobo = amountKobo;
        this.balanceAfterKobo = balanceAfterKobo;
        this.type = type;
        this.createdAt = Instant.now();
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getTxId() {
        return txId;
    }

    public void setTxId(UUID txId) {
        this.txId = txId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public long getAmountKobo() {
        return amountKobo;
    }

    public void setAmountKobo(long amountKobo) {
        this.amountKobo = amountKobo;
    }

    public long getBalanceAfterKobo() {
        return balanceAfterKobo;
    }

    public void setBalanceAfterKobo(long balanceAfterKobo) {
        this.balanceAfterKobo = balanceAfterKobo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "LedgerEntry{id=" + id +
                ", txId=" + txId +
                ", accountId='" + accountId + '\'' +
                ", amountKobo=" + amountKobo +
                ", type='" + type + '\'' +
                ", createdAt=" + createdAt + '}';
    }

}



