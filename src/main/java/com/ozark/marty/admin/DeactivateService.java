package com.ozark.marty.admin;

import com.ozark.marty.accounts.AccountDetailsResponse;
import com.ozark.marty.accounts.AccountRequest;
import com.ozark.marty.accounts.MyAccountsRequest;
import com.ozark.marty.entities.Users;
import com.ozark.marty.repository.AccountTableRepository;
import com.ozark.marty.repository.UserRepository;
import com.ozark.marty.table.AccountTable;
import com.ozark.marty.table.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeactivateService {

    private final AccountTableRepository accountTableRepository;
    private final UserRepository userRepository;

    public void deactivate(AccountRequest request){
        final Optional<AccountTable> accountTable = accountTableRepository.findByAccountID(request.getAccountID());
        if(accountTable.isPresent()){
            var account = accountTable.get();
            account.setStatus(Status.INACTIVE);
            account.setLastModified(LocalDateTime.now());
            accountTableRepository.save(account);
        } else {
            throw new RuntimeException("Account not available");
        }
    }

    public void delete(AccountRequest request){
        accountTableRepository.deleteById(request.getAccountID());
    }

    public void activate(AccountRequest request){
        final Optional<AccountTable> accountTable = accountTableRepository.findByAccountID(request.getAccountID());
        if(accountTable.isPresent()){
            var account = accountTable.get();
            account.setStatus(Status.ACTIVE);
            account.setLastModified(LocalDateTime.now());
            accountTableRepository.save(account);
        } else {
            throw new RuntimeException("Account not available");
        }
    }

    public List<AccountDetailsResponse> getAllUserAccounts(MyAccountsRequest request){
        //Find user by email
        Optional<Users> users = Optional.ofNullable(userRepository.findByEmail(request.getEmail()));
        if(users.isPresent()){
            Users userAccounts = users.get();
            //Retrieve all accounts associated with the user
            List<AccountTable> accountTables = userAccounts.getAccounts();
            //Convert accountTable objects to AccountDetailsResponse objects
            List<AccountDetailsResponse> accountDetailsResponses = accountTables.stream()
                    .map(accountTable -> AccountDetailsResponse.builder()
                            .accountID(accountTable.getAccountID())
                            .accountType(accountTable.getAccountType())
                            .balance(accountTable.getBalance())
                            .status(accountTable.getStatus())
                            .openDate(accountTable.getOpenDate())
                            .build())
                    .collect(Collectors.toList());
            return accountDetailsResponses;
        } else {
            throw new RuntimeException("User not found for email : " + request.getEmail());
        }
    }
}
