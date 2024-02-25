package com.ozark.marty.accounts;

import com.ozark.marty.table.AccountType;
import com.ozark.marty.table.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Bag;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailsResponse {
    private String accountID;
    private AccountType accountType;
    private Integer balance;
    private LocalDateTime openDate;
    private Status status;
}
