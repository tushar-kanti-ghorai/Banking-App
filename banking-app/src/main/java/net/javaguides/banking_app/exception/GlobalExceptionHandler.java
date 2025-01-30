package net.javaguides.banking_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Handle specific exception-AccountException
    @ExceptionHandler(AccountExcepttion.class)
   public ResponseEntity<ErrorDetails> handleAccountException(AccountExcepttion excepttion, WebRequest webRequest){
       ErrorDetails errorDetails=new ErrorDetails(
               LocalDateTime.now(),
               excepttion.getMessage(),
               webRequest.getDescription(false),
              "ACCOUNT_NOT_FOUND"
       );
       return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
   }
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorDetails> handleInsufficientBalanceException(InsufficientBalanceException excepttion, WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(
                LocalDateTime.now(),
                excepttion.getMessage(),
                webRequest.getDescription(false),
                "Insufficient Balance"
        );
        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
    }
   //Handle Generic Exception
    @ExceptionHandler(Exception.class)
   public ResponseEntity<ErrorDetails> handleGenericException(AccountExcepttion excepttion, WebRequest webRequest){
       ErrorDetails errorDetails=new ErrorDetails(
               LocalDateTime.now(),
               excepttion.getMessage(),
               webRequest.getDescription(false),
               "INTERNAL_SERVER_ERROR"
       );
       return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
   }
}
