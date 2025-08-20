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
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/digitalwallet/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<TransactionResponseDTO>> getStatement(@PathVariable String accountNumber) {
        Optional<Account> account = accountService.getAccountNumber(accountNumber);

        List<Transaction> transactions = transactionService.getStatement(account);

        List<TransactionResponseDTO> response = transactions.stream()
                .map(TransactionResponseDTO::new)
                .toList();

        return ResponseEntity.ok(response);
    }
}
