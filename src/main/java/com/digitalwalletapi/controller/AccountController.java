package com.digitalwalletapi.controller;

import com.digitalwalletapi.dto.AccountResponseDTO;
import com.digitalwalletapi.dto.AmountDTO;
import com.digitalwalletapi.dto.CreateAccountRequestDTO;
import com.digitalwalletapi.model.Account;
import com.digitalwalletapi.model.User;
import com.digitalwalletapi.service.AccountService;
import com.digitalwalletapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/digitalwallet/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody CreateAccountRequestDTO request) {
        User user = userService.getById(request.getUserId());

        Account account = accountService.createAccountForUser(user);

        return ResponseEntity.ok(new AccountResponseDTO(account));
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long id){
        return ResponseEntity.ok(accountService.getBalance(id));
    }

    @PostMapping("/{id}/credit")
    public ResponseEntity<Void> credit(@PathVariable Long id, @RequestBody AmountDTO amount){
        accountService.credit(id, amount.amount());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/debit")
    public ResponseEntity<Void> debit(@PathVariable Long id, @RequestBody AmountDTO amount){
        accountService.debit(id,amount.amount());
        return ResponseEntity.ok().build();
    }
}
