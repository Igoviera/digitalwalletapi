package br.com.igo.accountservice.controller;

import br.com.igo.accountservice.dto.AccountRequestDTO;
import br.com.igo.accountservice.dto.AccountResponseDTO;
import br.com.igo.accountservice.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> create(@Valid @RequestBody AccountRequestDTO dto) {
        return ResponseEntity.ok(accountService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> findAll() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<AccountResponseDTO> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.findByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> closeAccount(@PathVariable Long id) {
        accountService.closeAccount(id);
        return ResponseEntity.noContent().build();
    }
}
