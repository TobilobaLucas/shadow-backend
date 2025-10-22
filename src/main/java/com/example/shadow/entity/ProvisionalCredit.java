package com.example.shadow.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "provisional_credits")
public class ProvisionalCredit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // ⚠️ Matches tx.getId()
    @Column(name = "tx_id", nullable = false)
    private UUID txId;

    // ⚠️ Matches riskScore
    @Column(name = "risk_score")
    private double riskScore;

    // ⚠️ Matches expiresAt
    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    // -------- Constructors --------
    public ProvisionalCredit() {}

    public ProvisionalCredit(UUID id, UUID txId, double riskScore,
                             Instant expiresAt, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.txId = txId;
        this.riskScore = riskScore;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // -------- Getters & Setters --------
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getTxId() { return txId; }
    public void setTxId(UUID txId) { this.txId = txId; }

    public double getRiskScore() { return riskScore; }
    public void setRiskScore(double riskScore) { this.riskScore = riskScore; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    // -------- Lifecycle Hooks --------
    @PrePersist
    void prePersist() {
        createdAt = updatedAt = Instant.now();
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = Instant.now();
    }

    // -------- Manual Builder --------
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ProvisionalCredit pc = new ProvisionalCredit();

        public Builder txId(UUID txId) {
            pc.setTxId(txId);
            return this;
        }

        public Builder riskScore(double riskScore) {
            pc.setRiskScore(riskScore);
            return this;
        }

        public Builder expiresAt(Instant expiresAt) {
            pc.setExpiresAt(expiresAt);
            return this;
        }

        public ProvisionalCredit build() {
            return pc;
        }
    }

    @Override
    public String toString() {
        return "ProvisionalCredit{id=" + id + ", txId=" + txId + ", riskScore=" + riskScore + ", expiresAt=" + expiresAt + "}";
    }

}



//package com.example.shadow.entity;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.*;
//
//import java.time.Instant;
//import java.util.UUID;
//
//@Entity
//@Table(name = "provisional")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class ProvisionalCredit {
//    @Id
//    @Column(name = "tx_id")
//    private UUID txId;
//    @Column(name = "expires_at")
//    private Instant expiresAt;
//    @Column(name = "risk_score")
//    private double riskScore;
//}
