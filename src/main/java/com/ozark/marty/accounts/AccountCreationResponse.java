package com.ozark.marty.accounts;

import com.ozark.marty.table.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreationResponse {
    private String accountID;
    private Status status;
    private LocalDateTime creationTime;
    private Integer balance;
}
