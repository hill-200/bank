package com.ozark.marty.transaction;

import com.ozark.marty.accounts.AccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(
            @RequestBody TransactionRequest request
    ){
        return ResponseEntity.ok(service.withdraw(request));
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(
            @RequestBody TransactionRequest request
    ){
        return ResponseEntity.ok(service.deposit(request));
    }

}
