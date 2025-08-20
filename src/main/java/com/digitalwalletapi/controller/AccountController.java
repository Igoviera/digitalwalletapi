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

    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable String accountNumber){
        return ResponseEntity.ok(accountService.getBalance(accountNumber));
    }

    @PostMapping("/{accountNumber}/credit")
    public ResponseEntity<byte[]> credit(@PathVariable String accountNumber, @RequestBody AmountDTO amount) throws DocumentException {
        Transaction transaction = accountService.credit(accountNumber, amount.amount());

        byte[] pdf = pdfService.generateTransactionReceipt(transaction);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "comprovante.pdf");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    @PostMapping("/{accountNumber}/debit")
    public ResponseEntity<byte[]> debit(@PathVariable String accountNumber, @RequestBody AmountDTO amount) throws DocumentException {
        Transaction transaction = accountService.debit(accountNumber,amount.amount());

        byte[] pdf = pdfService.generateTransactionReceipt(transaction);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "comprovante.pdf");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    @PostMapping("/{accountNumber}/transfer")
    public ResponseEntity<byte[]> transfer(
            @PathVariable("accountNumber") String sourceAccountNumber,
            @RequestBody TransferRequestDTO dto
    ) throws Exception {
        Transaction tx = accountService.transfer(sourceAccountNumber, dto.targetAccountNumber(), dto.amount());

        byte[] pdf = pdfService.generateTransactionReceipt(tx);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "comprovante-transferencia.pdf");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}
