package com.bankmanagement.service.impl;

import com.bankmanagement.dto.TransactionDTO;
import com.bankmanagement.entity.Transaction;
import com.bankmanagement.repository.TransactionRepository;
import com.bankmanagement.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public Page<TransactionDTO> getByAccountId(Long accountId, String type, String startDate, String endDate, Pageable pageable) {
        LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate, DateTimeFormatter.ISO_DATE_TIME) : null;
        LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate, DateTimeFormatter.ISO_DATE_TIME) : null;
        if (type != null) {
            return transactionRepository.findByAccountIdAndType(accountId, Transaction.Type.valueOf(type), pageable)
                    .map(this::toDTO);
        } else if (start != null && end != null) {
            return transactionRepository.findByAccountIdAndTimestampBetween(accountId, start, end, pageable)
                    .map(this::toDTO);
        } else {
            return transactionRepository.findByAccountId(accountId, pageable)
                    .map(this::toDTO);
        }
    }

    @Override
    public Page<TransactionDTO> getAll(Pageable pageable) {
        return transactionRepository.findAll(pageable).map(this::toDTO);
    }

    private TransactionDTO toDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .type(transaction.getType().name())
                .amount(transaction.getAmount())
                .timestamp(transaction.getTimestamp())
                .description(transaction.getDescription())
                .accountId(transaction.getAccount().getId())
                .targetAccountNumber(transaction.getTargetAccountNumber())
                .build();
    }
} 