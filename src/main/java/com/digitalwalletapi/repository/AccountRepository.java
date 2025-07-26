package com.digitalwalletapi.repository;

import com.digitalwalletapi.model.Account;
import com.digitalwalletapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
   Optional<Account> findByUser(User user);
}
