package net.javaguides.banking_app.DTO;


import lombok.Data;

@Data
public class AccountDto {
    private long id;
    private String accountHolderName;
    private double balance;

    public AccountDto(long id, String accountHolderName, double balance) {
        this.id = id;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }
}
