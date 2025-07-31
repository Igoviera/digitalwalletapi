package com.digitalwalletapi.service;

import com.digitalwalletapi.enums.TransactionType;
import com.digitalwalletapi.model.Account;
import com.digitalwalletapi.model.Transaction;
import com.digitalwalletapi.model.User;
import com.digitalwalletapi.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountServiceImp implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    @Override
    public Account creatAccount(User user) {
        accountRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Usuário já possui uma conta"));

        Account account = new Account(user);

        return accountRepository.save(account);
    }

    @Override
    public BigDecimal getBalance(Long id) {
        return getAccount(id).getBalance();
    }

    @Override
    @Transactional
    public Transaction credit(Long accountId, BigDecimal amount) {
        Account account = getAccount(accountId);
        account.credit(amount);

        accountRepository.save(account);
        return transactionService.register(account, TransactionType.DEPOSITO, amount, null);
    }

    @Override
    @Transactional
    public Transaction debit(Long accountId, BigDecimal amount) {
        Account account = getAccount(accountId);
        account.debit(amount);

        accountRepository.save(account);
        return transactionService.register(account, TransactionType.SAQUE, amount, null);

    }

    @Override
    public Account getAccountById(Long accountId) {
        return getAccount(accountId);
    }

    @Override
    public Account createAccountForUser(User user) {
        Account account = new Account(user);
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Transaction transfer(Long sourceAccountId, Long targetAccountId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor da transferência deve ser positivo.");
        }

        if (sourceAccountId.equals(targetAccountId)){
            throw new IllegalArgumentException("A conta de origem e destino não podem ser iguais.");
        }

        Account sourceAccount = getAccount(sourceAccountId);
        Account targetAccount = getAccount(targetAccountId);

        if (sourceAccount == null || targetAccount == null) {
            throw new IllegalArgumentException("Conta de origem ou destino não encontrada.");
        }

        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }

        sourceAccount.debit(amount);
        accountRepository.save(sourceAccount);

        targetAccount.credit(amount);
        accountRepository.save(targetAccount);

        transactionService.register(sourceAccount, TransactionType.TRANSFERENCIA, amount.negate(), targetAccount);
        return transactionService.register(targetAccount, TransactionType.TRANSFERENCIA_RECEBIDA, amount, sourceAccount);
    }

    private Account getAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));
    }
}
