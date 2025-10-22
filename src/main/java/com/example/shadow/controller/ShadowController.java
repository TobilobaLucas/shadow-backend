package com.example.shadow.controller;

import com.example.shadow.entity.*;
import com.example.shadow.repo.TxRepo;
import com.example.shadow.service.ShadowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ShadowController {

    private final ShadowService shadowService;
    private final TxRepo txRepo;

    // Manual constructor injection
    public ShadowController(ShadowService shadowService, TxRepo txRepo) {
        this.shadowService = shadowService;
        this.txRepo = txRepo;
    }

    // Receive a new transaction
    @PostMapping("/nip/credit")
    public Transaction receiveCredit(@RequestParam String nipRef,
                                     @RequestParam String accountId,
                                     @RequestParam long amountKobo) {
        return shadowService.receiveCredit(nipRef, accountId, amountKobo);
    }

    //Move transaction to PROVISIONAL
    @PostMapping("/provisional/{txId}")
    public Transaction provisionalCredit(@PathVariable UUID txId,
                                         @RequestParam(defaultValue = "0.1") double riskScore,
                                         @RequestParam(defaultValue = "900") long expiresSeconds) {
        return shadowService.provisionalCredit(txId, riskScore, expiresSeconds);
    }

    //Post (finalize) a transaction
    @PostMapping("/post/{txId}")
    public Transaction postCredit(@PathVariable UUID txId) {
        return shadowService.postCredit(txId);
    }

    //Reverse a transaction
    @PostMapping("/reverse/{txId}")
    public Transaction reverseCredit(@PathVariable UUID txId,
                                     @RequestParam(defaultValue = "NIP_FAILED") String reason) {
        return shadowService.reverseCredit(txId, reason);
    }

    //List all transactions
    @GetMapping("/transactions")
    public List<Transaction> listTransactions() {
        return txRepo.findAll();
    }

    //Get all ledger entries for an account
    @GetMapping("/ledger/{accountId}")
    public List<LedgerEntry> accountLedger(@PathVariable String accountId) {
        return shadowService.getAccountLedger(accountId);
    }
}
