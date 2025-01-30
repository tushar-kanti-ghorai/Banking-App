package net.javaguides.banking_app.exception;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timestamp,
                           String message,
                           String details,
                           String errorCode) {
//    public ErrorDetails(LocalDateTime timestamp, String message, String details) {
//        this(timestamp, message, details, null);
//    }


//    public ErrorDetails(LocalDateTime timestamp, String message, String details, String errorCode) {
//        this.timestamp = timestamp;
//        this.message = message;
//        this.details = details;
//        this.errorCode = errorCode;
//    }
}
