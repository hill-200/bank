package com.ozark.marty.admin;

import com.ozark.marty.accounts.AccountDetailsResponse;
import com.ozark.marty.accounts.AccountRequest;
import com.ozark.marty.accounts.MyAccountsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class Deactivate {

    private final DeactivateService service;

    @PutMapping("/deactivate")
    public ResponseEntity<String> deactivate(@RequestBody AccountRequest request){
        service.deactivate(request);
        return ResponseEntity.ok("Successfully deactivated account : " + request.getAccountID());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody AccountRequest request){
        service.delete(request);
        return ResponseEntity.ok("Successfully deleted account : " + request.getAccountID());
    }

    @PutMapping("/activate")
    public ResponseEntity<String> activate(@RequestBody AccountRequest request){
        service.activate(request);
        return ResponseEntity.ok("Successfully activated account : " + request.getAccountID());
    }

    @GetMapping("/userAccounts")
    public ResponseEntity<List<AccountDetailsResponse>> getAllUserAccounts(@RequestBody MyAccountsRequest request){
        return ResponseEntity.ok(service.getAllUserAccounts(request));
    }

}
