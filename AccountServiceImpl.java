package com.bankmanagement.service.impl;

import com.bankmanagement.dto.*;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.entity.Transaction;
import com.bankmanagement.exception.BadRequestException;
import com.bankmanagement.exception.ResourceNotFoundException;
import com.bankmanagement.repository.AccountRepository;
import com.bankmanagement.repository.CustomerRepository;
import com.bankmanagement.repository.TransactionRepository;
import com.bankmanagement.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public AccountDTO create(AccountCreateDTO createDTO) {
        Customer customer = customerRepository.findById(createDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Account account = Account.builder()
                .accountNumber(generateAccountNumber())
                .type(Account.Type.valueOf(createDTO.getType()))
                .balance(createDTO.getInitialDeposit())
                .status(Account.Status.ACTIVE)
                .customer(customer)
                .build();
        account = accountRepository.save(account);
        return toDTO(account);
    }

    @Override
    public AccountDTO getById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        return toDTO(account);
    }

    @Override
    public List<AccountSummaryDTO> getByCustomerId(Long customerId) {
        return accountRepository.findByCustomerId(customerId).stream()
                .map(this::toSummaryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void close(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        account.setStatus(Account.Status.CLOSED);
        accountRepository.save(account);
    }

    @Override
    public AccountDTO deposit(DepositWithdrawDTO depositDTO) {
        Account account = accountRepository.findByAccountNumber(depositDTO.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        account.setBalance(account.getBalance().add(depositDTO.getAmount()));
        account = accountRepository.save(account);
        createTransaction(account, Transaction.Type.DEPOSIT, depositDTO.getAmount(), "Deposit");
        return toDTO(account);
    }

    @Override
    public AccountDTO withdraw(DepositWithdrawDTO withdrawDTO) {
        Account account = accountRepository.findByAccountNumber(withdrawDTO.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        if (account.getBalance().compareTo(withdrawDTO.getAmount()) < 0) {
            throw new BadRequestException("Insufficient balance");
        }
        account.setBalance(account.getBalance().subtract(withdrawDTO.getAmount()));
        account = accountRepository.save(account);
        createTransaction(account, Transaction.Type.WITHDRAW, withdrawDTO.getAmount(), "Withdraw");
        return toDTO(account);
    }

    @Override
    public void transfer(TransferDTO transferDTO) {
        Account fromAccount = accountRepository.findByAccountNumber(transferDTO.getFromAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("From account not found"));
        Account toAccount = accountRepository.findByAccountNumber(transferDTO.getToAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("To account not found"));
        if (fromAccount.getBalance().compareTo(transferDTO.getAmount()) < 0) {
            throw new BadRequestException("Insufficient balance");
        }
        fromAccount.setBalance(fromAccount.getBalance().subtract(transferDTO.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(transferDTO.getAmount()));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        createTransaction(fromAccount, Transaction.Type.TRANSFER, transferDTO.getAmount(), "Transfer to " + transferDTO.getToAccountNumber());
    }

    @Override
    public List<AccountDTO> getAll() {
        return accountRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private String generateAccountNumber() {
        return String.format("%010d", System.nanoTime() % 10000000000L);
    }

    private void createTransaction(Account account, Transaction.Type type, BigDecimal amount, String description) {
        Transaction transaction = Transaction.builder()
                .type(type)
                .amount(amount)
                .timestamp(LocalDateTime.now())
                .description(description)
                .account(account)
                .build();
        transactionRepository.save(transaction);
    }

    private AccountDTO toDTO(Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .type(account.getType().name())
                .balance(account.getBalance())
                .status(account.getStatus().name())
                .customerId(account.getCustomer().getId())
                .transactions(List.of()) // Fill with transactions if needed
                .build();
    }

    private AccountSummaryDTO toSummaryDTO(Account account) {
        return AccountSummaryDTO.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .type(account.getType().name())
                .balance(account.getBalance())
                .status(account.getStatus().name())
                .build();
    }
} 