package com.bankmanagement.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {
    private Long id;
    private String accountNumber;
    private String type;
    private BigDecimal balance;
    private String status;
    private Long customerId;
    private List<TransactionDTO> transactions;
} 