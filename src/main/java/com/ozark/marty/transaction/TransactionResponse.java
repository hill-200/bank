package com.ozark.marty.transaction;

import com.ozark.marty.table.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String accountID;
    private LocalDateTime transactionDate;
    private TransactionType transactionType;
    private Integer transactionID;
    private Integer balance;
    private String message;
    private Integer amount;
}
