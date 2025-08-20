package com.digitalwalletapi.service;

import com.digitalwalletapi.model.Account;
import com.digitalwalletapi.model.Transaction;
import com.digitalwalletapi.model.User;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountService {
    Account createAccount(User user);
    BigDecimal getBalance(String accountNumber);
    Transaction credit (String accountNumber, BigDecimal amount);
    Transaction debit (String accountNumber, BigDecimal amount);
    Optional<Account> getAccountNumber(String accountNumber);
    //Account createAccountForUser(User user);
    Transaction transfer(String sourceAccountNumber, String targetAccountNumber, BigDecimal amount);
}
