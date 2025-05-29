package com.bankmanagement.controller;

import com.bankmanagement.dto.TransactionDTO;
import com.bankmanagement.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/account/{accountId}")
    public ResponseEntity<Page<TransactionDTO>> getByAccountId(
            @PathVariable Long accountId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Pageable pageable) {
        return ResponseEntity.ok(transactionService.getByAccountId(accountId, type, startDate, endDate, pageable));
    }

    @GetMapping
    public ResponseEntity<Page<TransactionDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(transactionService.getAll(pageable));
    }
} 