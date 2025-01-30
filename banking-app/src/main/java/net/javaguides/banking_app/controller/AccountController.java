package net.javaguides.banking_app.controller;

import net.javaguides.banking_app.DTO.AccountDto;
import net.javaguides.banking_app.DTO.TransactionDto;
import net.javaguides.banking_app.DTO.TransferFundDto;
import net.javaguides.banking_app.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //Add Account Rest API
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    //Get Account Rest API
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
        AccountDto accountDto=accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    //Deposite Rest ApI
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposite(@PathVariable Long id, @RequestBody Map<String,Double> request){
        double amount=request.get("amount");
        AccountDto accountDto=accountService.deposite(id,amount);
        return ResponseEntity.ok(accountDto);
    }
    //withdraw Rest API
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id, @RequestBody Map<String,Double> request){
        double amount=request.get("amount");
        AccountDto accountDto=accountService.withdraw(id,amount);
        return ResponseEntity.ok(accountDto);
    }

    //Get All Accounts
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accounts=accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

//Delete Account Rest API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
     accountService.deleteAccount(id);
     return ResponseEntity.ok("Account is deleted successfully");
    }
//Build trabsfer Rest API
    @PostMapping("/transfer")
    public ResponseEntity<String> transferFund(@RequestBody TransferFundDto transferFundDto) {
        accountService.transferFunds(transferFundDto);
        return ResponseEntity.ok("Transfer Successfull");
    }

//Build transaction REST API
    @GetMapping("/{id}/transactions")
   public ResponseEntity<List<TransactionDto>> fetchAccountTransactions(@PathVariable("id") Long accountId){
        List<TransactionDto> transactions=accountService.getAccountTransactions(accountId);
        return ResponseEntity.ok(transactions);
   }

}
