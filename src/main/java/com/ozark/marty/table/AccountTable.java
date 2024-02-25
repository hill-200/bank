package com.ozark.marty.table;

import com.ozark.marty.entities.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccountTable {
    @Id
    @Column(unique = true, nullable = false)
    private String accountID;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users userID;

    @OneToMany
    @JoinColumn(name="transaction_id")
    private List<TransactionTable> transactionTables;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Column(nullable = false)
    private Integer balance;

    @Column(nullable = false)
    private LocalDateTime openDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private LocalDateTime lastModified;
}
