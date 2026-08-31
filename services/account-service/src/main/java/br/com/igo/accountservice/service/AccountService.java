package br.com.igo.accountservice.service;

import br.com.igo.accountservice.dto.AccountRequestDTO;
import br.com.igo.accountservice.dto.AccountResponseDTO;
import br.com.igo.accountservice.entity.Account;
import br.com.igo.accountservice.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponseDTO create(AccountRequestDTO dto){
        if (accountRepository.findByUserId(dto.userId()).isPresent()){
            throw new RuntimeException("Usuário já possui uma conta");
        }

        String accountNumber =  generateAccountNumber();

        Account account = new Account(
                dto.userId(),
                accountNumber
        );

        Account savedAcoount = accountRepository.save(account);

        return toResponseDTO(savedAcoount);
    }



    private String generateAccountNumber() {

        Random random = new Random();

        String accountNumber;

        do {
            accountNumber = String.format(
                    "%08d",
                    random.nextInt(100_000_000)
            );
        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }

    private AccountResponseDTO toResponseDTO(Account account) {

        return new AccountResponseDTO(
                account.getId(),
                account.getUserId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getStatus(),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }
}
