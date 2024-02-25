package com.ozark.marty.transaction;

import com.ozark.marty.accounts.AccountRequest;
import com.ozark.marty.repository.AccountTableRepository;
import com.ozark.marty.repository.TransactionTableRepository;
import com.ozark.marty.table.AccountTable;
import com.ozark.marty.table.Status;
import com.ozark.marty.table.TransactionTable;
import com.ozark.marty.table.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountTableRepository accountTableRepository;
    private final TransactionTableRepository transactionTableRepository;

    public TransactionResponse withdraw(TransactionRequest request){
        final Integer amount = request.getAmount();
        final Optional<AccountTable> account = accountTableRepository.findByAccountID(request.getAccountID());
        if(account.isPresent()) {
            var accountBalance = account.get();
            var balance = accountBalance.getBalance();
            TransactionTable transaction = null;
            if(accountBalance.getStatus() == Status.INACTIVE){
                return TransactionResponse.builder()
                        .message("Sorry! The account is currently deactivated. Kindly visit any of our branch near you.")
                        .build();
            }
            if (amount >= balance) {
                return TransactionResponse.builder()
                        .message("Insufficient balance")
                        .build();
            } else {
                var total = balance - amount;
                accountBalance.setBalance(total);
                accountBalance.setLastModified(LocalDateTime.now());
                accountTableRepository.save(accountBalance);
                transaction = TransactionTable.builder()
                        .accountID(accountBalance)
                        .transactionDate(LocalDateTime.now())
                        .amount(request.getAmount())
                        .build();
                if (Objects.equals(request.getTransactionType(), "WITHDRAW")) {
                    transaction.setTransactionType(TransactionType.WITHDRAW);
                }
                transactionTableRepository.save(transaction);
            }
            return TransactionResponse.builder()
                    .message("Transaction successful")
                    .transactionDate(transaction.getTransactionDate())
                    .accountID(request.getAccountID())
                    .balance(accountBalance.getBalance())
                    .transactionType(transaction.getTransactionType())
                    .build();

        } else{
            throw new RuntimeException("Transaction was unsuccessful");
        }
    }

    public TransactionResponse deposit(TransactionRequest request){
        final Integer amount = request.getAmount();
        final Optional<AccountTable> account = accountTableRepository.findByAccountID(request.getAccountID());
        if(account.isPresent()){
            var accountBalance = account.get();
            var balance = accountBalance.getBalance();
            TransactionTable transaction = null;
            //var depositAmount = request.getAmount();
            if(accountBalance.getStatus() == Status.INACTIVE){
                return TransactionResponse.builder()
                        .message("Sorry! The account is currently deactivated. Kindly visit any of our branch near you.")
                        .build();
            }
            if(amount <= 150000){
                var total = balance + amount;
                accountBalance.setBalance(total);
                accountBalance.setLastModified(LocalDateTime.now());
                accountTableRepository.save(accountBalance);
                transaction = TransactionTable.builder()
                        .accountID(accountBalance)
                        .transactionDate(LocalDateTime.now())
                        .amount(amount)
                        .build();
                if(Objects.equals(request.getTransactionType(), "DEPOSIT")){
                    transaction.setTransactionType(TransactionType.DEPOSIT);
                }
                transactionTableRepository.save(transaction);
            }
            assert transaction != null;
            return TransactionResponse.builder()
                    .message("Money Successfully deposited to account : " + request.getAccountID())
                    .balance(accountBalance.getBalance())
                    .accountID(request.getAccountID())
                    .transactionDate(transaction.getTransactionDate())
                    .transactionType(transaction.getTransactionType())
                    .build();
        }else {
            throw new RuntimeException("Cannot deposit that amount. Please enter a lesser amount");
        }
    }

}

