package net.javaguides.banking_app.DTO;

public record TransferFundDto(Long fromAccountId,
                              Long toAccountId,
                              double amount) {

}
