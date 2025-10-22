package com.example.shadow.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nip_ref", unique = true, nullable = false)
    private String nipRef;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(name = "amount_kobo", nullable = false)
    private long amountKobo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private TxStatus status;

    private String reason;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    // -------- Constructors --------
    public Transaction() {}

    public Transaction(UUID id, String nipRef, String accountId, long amountKobo,
                       TxStatus status, String reason, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.nipRef = nipRef;
        this.accountId = accountId;
        this.amountKobo = amountKobo;
        this.status = status;
        this.reason = reason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // -------- Getters and setters --------
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNipRef() { return nipRef; }
    public void setNipRef(String nipRef) { this.nipRef = nipRef; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public long getAmountKobo() { return amountKobo; }
    public void setAmountKobo(long amountKobo) { this.amountKobo = amountKobo; }

    public TxStatus getStatus() { return status; }
    public void setStatus(TxStatus status) { this.status = status; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    // -------- Lifecycle hooks --------
    @PrePersist
    void prePersist() {
        createdAt = updatedAt = Instant.now();
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = Instant.now();
    }

    // -------- Manual builder --------
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Transaction t = new Transaction();

        public Builder nipRef(String nipRef) {
            t.setNipRef(nipRef);
            return this;
        }

        public Builder accountId(String accountId) {
            t.setAccountId(accountId);
            return this;
        }

        public Builder amountKobo(long amountKobo) {
            t.setAmountKobo(amountKobo);
            return this;
        }

        public Builder status(TxStatus status) {
            t.setStatus(status);
            return this;
        }

        public Builder reason(String reason) {
            t.setReason(reason);
            return this;
        }

        public Transaction build() {
            return t;
        }
    }

    // -------- Enum --------
    public enum TxStatus {
        RECEIVED, PROVISIONAL, POSTED, REVERSED, FAILED
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", nipRef='" + nipRef + '\'' +
                ", accountId='" + accountId + '\'' +
                ", amountKobo=" + amountKobo +
                ", status=" + status +
                ", reason='" + reason + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

}



//package com.example.shadow.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import java.time.*;
//import java.util.*;
//
//@Entity @Table(name="transactions")
//@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
//public class Transaction {
//    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
//
//    @Column(name="nip_ref", unique=true, nullable=false)
//    private String nipRef;
//
//    @Column(name="account_id", nullable=false)
//    private String accountId;
//
//    @Column(name="amount_kobo", nullable=false)
//    private long amountKobo;
//
//    @Enumerated(EnumType.STRING) @Column(nullable=false, length=16)
//    private TxStatus status;
//
//    private String reason;
//
//    @Column(name="created_at") private Instant createdAt;
//    @Column(name="updated_at") private Instant updatedAt;
//
//    @PrePersist void pre(){ createdAt = updatedAt = Instant.now(); }
//    @PreUpdate  void upd(){ updatedAt = Instant.now(); }
//
//    public enum TxStatus { RECEIVED, PROVISIONAL, POSTED, REVERSED, FAILED }
//}
//
