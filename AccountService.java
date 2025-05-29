package com.bankmanagement.service;

import com.bankmanagement.dto.*;
import java.util.List;

public interface AccountService {
    AccountDTO create(AccountCreateDTO createDTO);
    AccountDTO getById(Long id);
    List<AccountSummaryDTO> getByCustomerId(Long customerId);
    void close(Long id);
    AccountDTO deposit(DepositWithdrawDTO depositDTO);
    AccountDTO withdraw(DepositWithdrawDTO withdrawDTO);
    void transfer(TransferDTO transferDTO);
    List<AccountDTO> getAll();
} 