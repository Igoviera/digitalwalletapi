package com.digitalwalletapi.service;

import com.digitalwalletapi.model.Account;
import com.digitalwalletapi.model.Transaction;
import com.digitalwalletapi.model.User;

import java.math.BigDecimal;

public interface AccountService {
    Account creatAccount(User user);
    BigDecimal getBalance(Long id);
    Transaction credit (Long accountId, BigDecimal amount);
    Transaction debit (Long accountId, BigDecimal amount);
    Account getAccountById(Long accountId);
    Account createAccountForUser(User user);
    Transaction transfer(Long sourceAccountId, Long targetAccountId, BigDecimal amount);
}
