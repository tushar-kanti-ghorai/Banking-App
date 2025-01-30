package net.javaguides.banking_app.service;

import net.javaguides.banking_app.DTO.AccountDto;
import net.javaguides.banking_app.DTO.TransactionDto;
import net.javaguides.banking_app.DTO.TransferFundDto;

import java.util.List;


public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccountById(Long id);
    AccountDto deposite(Long id,double amount);
    AccountDto withdraw(Long id,double amount);
    List<AccountDto> getAllAccounts();
    void deleteAccount(Long id);
    void transferFunds(TransferFundDto transferFundDto);
    List<TransactionDto> getAccountTransactions(Long accountId);

}
