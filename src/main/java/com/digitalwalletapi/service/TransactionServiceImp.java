package com.digitalwalletapi.service;

import com.digitalwalletapi.enums.TransactionType;
import com.digitalwalletapi.model.Account;
import com.digitalwalletapi.model.Transaction;
import com.digitalwalletapi.repository.AccountRepository;
import com.digitalwalletapi.repository.TransactionRepository;
import com.digitalwalletapi.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImp implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRespository userRespository;

    @Override
    public Transaction register(Account account, TransactionType type, BigDecimal amount, Account targetAccount) {
        Transaction transaction = new Transaction(account,type,amount,targetAccount);
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getStatement(Optional<Account> account) {
        return transactionRepository.findAllByAccountOrderByTimestampDesc(account);
    }
}
