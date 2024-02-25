package com.ozark.marty.accounts;

import com.ozark.marty.table.AccountTable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;
    @PostMapping("/create")
    public ResponseEntity<AccountCreationResponse> createAccount(
            @RequestBody AccountCreationRequest request
    ){
        return ResponseEntity.ok(service.createAccount(request));
    }

    @GetMapping("/myAccounts")
    public ResponseEntity<List<AccountDetailsResponse>> myAccounts(
         @RequestBody   MyAccountsRequest request
    ){
      return ResponseEntity.ok(service.findAllUserAccounts(request));
    }

    @GetMapping("/account-details")
    public ResponseEntity<AccountDetailsResponse> accountDetails(
        @RequestBody  AccountRequest request
    ){
        return ResponseEntity.ok(service.findAccountByAccountID(request));
    }
}
