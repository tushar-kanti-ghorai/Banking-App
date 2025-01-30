package net.javaguides.banking_app.mapper;

import net.javaguides.banking_app.DTO.AccountDto;
import net.javaguides.banking_app.entity.Account;

public class AccountMApper {
    public static Account mapToAccount(AccountDto accountDto){
        Account account =new Account(
                accountDto.getId(),
                accountDto.getAccountHolderName(),
                accountDto.getBalance()
        );
        return account;
    }

    public static AccountDto maptoAccountDto(Account account){
        AccountDto accountDto=new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getBalance()
        );
        return accountDto;
    }
}
