package com.digitalwalletapi.repository;

import com.digitalwalletapi.model.Account;
import com.digitalwalletapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByAccountOrderByTimestampDesc(Optional<Account> account);
}
