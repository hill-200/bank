package com.ozark.marty.accounts;

import com.ozark.marty.entities.Users;
import com.ozark.marty.repository.AccountTableRepository;
import com.ozark.marty.repository.UserRepository;
import com.ozark.marty.table.AccountTable;
import com.ozark.marty.table.AccountType;
import com.ozark.marty.table.Status;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountTableRepository accountTableRepository;
    private final UserRepository userRepository;

    public AccountCreationResponse createAccount(AccountCreationRequest request){
        final Users email;
        email = userRepository.findByEmail(request.getEmail());
        var accountID = generateRandomNumber();
        var account = AccountTable.builder()
                    .accountID(accountID)
                    .balance(0)
                    .openDate(LocalDateTime.now())
                    .status(Status.ACTIVE)
                    .userID(email)
                    .build();
        if(Objects.equals(request.getAccountType(), "NORMAL")){
            account.setAccountType(AccountType.NORMAL);
        } else if (Objects.equals(request.getAccountType(), "SAVING")) {
            account.setAccountType(AccountType.SAVING);
        }
        accountTableRepository.save(account);
        return AccountCreationResponse.builder()
                .accountID(account.getAccountID())
                .balance(account.getBalance())
                .status(account.getStatus())
                .creationTime(account.getOpenDate())
                .build();
    }
    private String generateRandomNumber(){
        Random random = new Random();
        Integer randomNumber = random.nextInt(100000000);
        return String.valueOf(randomNumber);
    }
    public List<AccountDetailsResponse> findAllUserAccounts(MyAccountsRequest request){
        final Users email;
        email = userRepository.findByEmail(request.getEmail());
        var userID = email.getId();
        final List<AccountTable> accounts = accountTableRepository.findAllAccountsByUserID(userID);
        AccountTable table = accounts.get(userID);
        return (List<AccountDetailsResponse>) AccountDetailsResponse.builder()
                .accountID(table.getAccountID())
                .accountType(table.getAccountType())
                .status(table.getStatus())
                .openDate(table.getOpenDate())
                .balance(table.getBalance())
                .build();
    }
    public AccountDetailsResponse findAccountByAccountID(AccountRequest request){
        final Optional<AccountTable> accountTable;
        accountTable = accountTableRepository.findByAccountID(request.getAccountID());
        if(accountTable.isPresent()){
            AccountTable table = accountTable.get();
            return AccountDetailsResponse.builder()
                    .accountID(table.getAccountID())
                    .accountType(table.getAccountType())
                    .balance(table.getBalance())
                    .openDate(table.getOpenDate())
                    .status(table.getStatus())
                    .build();
        }else {
            throw new RuntimeException("Account not found for ID : " + request.getAccountID());
        }
    }
}
