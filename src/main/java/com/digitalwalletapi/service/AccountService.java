package com.digitalwalletapi.service;

import com.digitalwalletapi.model.Account;
import com.digitalwalletapi.model.User;

import java.math.BigDecimal;

public interface AccountService {
    Account creatAccount(User user);
    BigDecimal getBalance(Long id);
    void credit (Long accountId, BigDecimal amount);
    void debit (Long accountId, BigDecimal amount);
}
