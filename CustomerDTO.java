package com.bankmanagement.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
    private String contact;
    private LocalDate dob;
    private String status;
    private List<AccountSummaryDTO> accounts;
} 