package com.bankmanagement.controller;

import com.bankmanagement.dto.*;
import com.bankmanagement.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDTO> create(@Valid @RequestBody AccountCreateDTO createDTO) {
        return ResponseEntity.ok(accountService.create(createDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AccountSummaryDTO>> getByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(accountService.getByCustomerId(customerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> close(@PathVariable Long id) {
        accountService.close(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deposit")
    public ResponseEntity<AccountDTO> deposit(@Valid @RequestBody DepositWithdrawDTO depositDTO) {
        return ResponseEntity.ok(accountService.deposit(depositDTO));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<AccountDTO> withdraw(@Valid @RequestBody DepositWithdrawDTO withdrawDTO) {
        return ResponseEntity.ok(accountService.withdraw(withdrawDTO));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@Valid @RequestBody TransferDTO transferDTO) {
        accountService.transfer(transferDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAll() {
        return ResponseEntity.ok(accountService.getAll());
    }
} 