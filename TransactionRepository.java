package com.bankmanagement.repository;

import com.bankmanagement.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByAccountId(Long accountId, Pageable pageable);
    Page<Transaction> findByAccountIdAndType(Long accountId, Transaction.Type type, Pageable pageable);
    Page<Transaction> findByAccountIdAndTimestampBetween(Long accountId, java.time.LocalDateTime start, java.time.LocalDateTime end, Pageable pageable);
} 