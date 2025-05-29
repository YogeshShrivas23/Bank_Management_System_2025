package com.bankmanagement.service;

import com.bankmanagement.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {
    Page<TransactionDTO> getByAccountId(Long accountId, String type, String startDate, String endDate, Pageable pageable);
    Page<TransactionDTO> getAll(Pageable pageable);
} 