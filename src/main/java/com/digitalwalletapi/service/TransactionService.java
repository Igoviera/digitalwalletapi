package com.digitalwalletapi.service;

import com.digitalwalletapi.enums.TransactionType;
import com.digitalwalletapi.model.Account;
import com.digitalwalletapi.model.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TransactionService {
    Transaction register(Account account, TransactionType type, BigDecimal amount, Account targetAccount);
    List<Transaction> getStatement(Optional<Account> account);
}
