package com.digitalwalletapi.controller;

import com.digitalwalletapi.dto.AccountResponseDTO;
import com.digitalwalletapi.dto.AmountDTO;
import com.digitalwalletapi.dto.CreateAccountRequestDTO;
import com.digitalwalletapi.dto.TransferRequestDTO;
import com.digitalwalletapi.model.Account;
import com.digitalwalletapi.model.Transaction;
import com.digitalwalletapi.model.User;
import com.digitalwalletapi.service.AccountService;
import com.digitalwalletapi.service.PdfService;
import com.digitalwalletapi.service.UserService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Autowired
    private PdfService pdfService;

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
    public ResponseEntity<byte[]> credit(@PathVariable Long id, @RequestBody AmountDTO amount) throws DocumentException {
        Transaction transaction = accountService.credit(id, amount.amount());

        byte[] pdf = pdfService.generateTransactionReceipt(transaction);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "comprovante.pdf");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    @PostMapping("/{id}/debit")
    public ResponseEntity<byte[]> debit(@PathVariable Long id, @RequestBody AmountDTO amount) throws DocumentException {
        Transaction transaction = accountService.debit(id,amount.amount());

        byte[] pdf = pdfService.generateTransactionReceipt(transaction);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "comprovante.pdf");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    @PostMapping("/{id}/transfer")
    public ResponseEntity<byte[]> transfer(
            @PathVariable("id") Long sourceAccountId,
            @RequestBody TransferRequestDTO dto
    ) throws Exception {
        Transaction tx = accountService.transfer(sourceAccountId, dto.targetAccountId(), dto.amount());

        byte[] pdf = pdfService.generateTransactionReceipt(tx);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "comprovante-transferencia.pdf");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}
