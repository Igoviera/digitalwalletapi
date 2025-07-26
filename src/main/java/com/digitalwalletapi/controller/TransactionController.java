package com.digitalwalletapi.controller;

import com.digitalwalletapi.dto.TransactionResponseDTO;
import com.digitalwalletapi.model.Account;
import com.digitalwalletapi.model.Transaction;
import com.digitalwalletapi.service.AccountService;
import com.digitalwalletapi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/digitalwallet/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountId}")
    public ResponseEntity<List<TransactionResponseDTO>> getStatement(@PathVariable Long accountId) {
        Account account = accountService.getAccountById(accountId);

        List<Transaction> statement = transactionService.getStatement(account);

        List<TransactionResponseDTO> response = statement.stream()
                .map(tx -> new TransactionResponseDTO(
                        tx.getId(),
                        tx.getType(),
                        tx.getAmount(),
                        tx.getTimestamp(),
                        tx.getTargetAccount() != null ? tx.getTargetAccount().getId() : null
                ))
                .toList();

        return ResponseEntity.ok(response);
    }
}
