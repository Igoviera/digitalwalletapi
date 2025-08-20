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
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImp implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    @Override
    public Account createAccount(User user) {
        String accountNumber = generateUniqueAccountNumber();
        Account account = new Account(user, accountNumber);

        return accountRepository.save(account);
    }

    @Override
    public BigDecimal getBalance(String accountNumber) {
        return accountNumber(accountNumber).getBalance();
    }

    @Override
    @Transactional
    public Transaction credit(String accountNumber, BigDecimal amount) {
        Account account = accountNumber(accountNumber);
        account.credit(amount);

        accountRepository.save(account);
        return transactionService.register(account, TransactionType.DEPOSITO, amount, null);
    }

    @Override
    @Transactional
    public Transaction debit(String accountNumber, BigDecimal amount) {
        Account account =accountNumber(accountNumber);

        account.debit(amount);

        accountRepository.save(account);
        return transactionService.register(account, TransactionType.SAQUE, amount, null);

    }

    @Override
    public Optional<Account> getAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

//    @Override
//    public Account createAccountForUser(User user) {
//        Account account = new Account(user);
//        return accountRepository.save(account);
//    }

    @Override
    @Transactional
    public Transaction transfer(String sourceAccountNumber, String targetAccountNumber, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor da transferência deve ser positivo.");
        }

        if (sourceAccountNumber.equals(targetAccountNumber)){
            throw new IllegalArgumentException("A conta de origem e destino não podem ser iguais.");
        }

        Account sourceAccount = accountNumber(sourceAccountNumber);
        Account targetAccount = accountNumber(targetAccountNumber);

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

    private Account accountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));
    }

    private String generateUniqueAccountNumber(){
        String accountNumber;
        do{
            accountNumber = String.format("%08d", new Random().nextInt(100_000_000));
        }while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }
}
