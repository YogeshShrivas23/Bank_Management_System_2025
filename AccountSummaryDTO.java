package com.bankmanagement.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountSummaryDTO {
    private Long id;
    private String accountNumber;
    private String type;
    private BigDecimal balance;
    private String status;
} 