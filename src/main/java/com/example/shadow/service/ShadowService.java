package com.example.shadow.service;

import com.example.shadow.entity.*;
import com.example.shadow.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShadowService {

    private final TxRepo txRepo;
    private final LedgerRepo ledgerRepo;
    private final ProvRepo provRepo;

    // Manual constructor injection (no Lombok)
    public ShadowService(TxRepo txRepo, LedgerRepo ledgerRepo, ProvRepo provRepo) {
        this.txRepo = txRepo;
        this.ledgerRepo = ledgerRepo;
        this.provRepo = provRepo;
    }

    // Receive a new credit transaction
    @Transactional
    public Transaction receiveCredit(String nipRef, String accountId, long amountKobo) {
        // Prevent duplicate NIP reference
        Optional<Transaction> existing = txRepo.findByNipRef(nipRef);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Duplicate NIP reference");
        }

        Transaction tx = new Transaction();
        tx.setNipRef(nipRef);
        tx.setAccountId(accountId);
        tx.setAmountKobo(amountKobo);
        tx.setStatus(Transaction.TxStatus.RECEIVED);
        txRepo.save(tx);

        return tx;
    }

    // Apply a provisional credit (temporary hold)
    @Transactional
    public Transaction provisionalCredit(UUID txId, double riskScore, long expiresSeconds) {
        Transaction tx = txRepo.findById(txId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        tx.setStatus(Transaction.TxStatus.PROVISIONAL);
        txRepo.save(tx);

        ProvisionalCredit pc = new ProvisionalCredit();
        pc.setTxId(tx.getId());
        pc.setRiskScore(riskScore);
        pc.setExpiresAt(Instant.now().plusSeconds(expiresSeconds));
        provRepo.save(pc);

        LedgerEntry le = new LedgerEntry(
                tx.getId(),
                tx.getAccountId(),
                tx.getAmountKobo(),
                tx.getAmountKobo(), // dummy balance update
                "CREDIT"
        );
        ledgerRepo.save(le);

        return tx;
    }

    //Confirm (post) a credit transaction
    @Transactional
    public Transaction postCredit(UUID txId) {
        Transaction tx = txRepo.findById(txId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        tx.setStatus(Transaction.TxStatus.POSTED);
        txRepo.save(tx);

        // remove provisional credit record
        provRepo.deleteById(tx.getId());

        return tx;
    }

    //Reverse a transaction
    @Transactional
    public Transaction reverseCredit(UUID txId, String reason) {
        Transaction tx = txRepo.findById(txId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        tx.setStatus(Transaction.TxStatus.REVERSED);
        tx.setReason(reason);
        txRepo.save(tx);

        LedgerEntry reversal = new LedgerEntry(
                tx.getId(),
                tx.getAccountId(),
                -tx.getAmountKobo(), // negative to reverse
                0L,
                "REVERSAL"
        );
        ledgerRepo.save(reversal);

        provRepo.deleteById(tx.getId());

        return tx;
    }

    // Retrieve account ledger entries
    public List<LedgerEntry> getAccountLedger(String accountId) {
        return ledgerRepo.findByAccountIdOrderByCreatedAtAsc(accountId);
    }
}
