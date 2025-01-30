package net.javaguides.banking_app.DTO;

import java.time.LocalDateTime;

public record TransactionDto(Long id,
                             Long accountId,
                             double amount,
                             String transactionType,
                             LocalDateTime timestamp) {
}
