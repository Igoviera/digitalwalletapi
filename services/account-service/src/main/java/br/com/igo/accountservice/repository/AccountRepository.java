package br.com.igo.accountservice.repository;

import br.com.igo.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserId(Long userId);
    boolean existsByAccountNumber(String accountNumber);
}
