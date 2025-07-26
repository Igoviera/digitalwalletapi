package com.digitalwalletapi.controller;

import com.digitalwalletapi.dto.AccountResponseDTO;
import com.digitalwalletapi.model.Account;
import com.digitalwalletapi.model.User;
import com.digitalwalletapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody User user) {
        Account account = accountService.creatAccount(user);
        return ResponseEntity.ok(new AccountResponseDTO(
                account.getId(),
                account.getBalance(),
                account.getCreatedAt()
        ));
    }

    public ResponseEntity<BigDecimal> getBalance(Long id){
        return ResponseEntity.ok(accountService.getBalance(id));
    }

    @PostMapping("/{id}/credit")
    public ResponseEntity<Void> credit(@PathVariable Long id, @RequestBody BigDecimal amount){
        accountService.credit(id, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/debit")
    public ResponseEntity<Void> debit(@PathVariable Long id, @RequestBody BigDecimal amount){
        accountService.debit(id,amount);
        return ResponseEntity.ok().build();
    }
}
