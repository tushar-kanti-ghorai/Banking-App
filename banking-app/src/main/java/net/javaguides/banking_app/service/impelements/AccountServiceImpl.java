package net.javaguides.banking_app.service.impelements;

import net.javaguides.banking_app.DTO.AccountDto;
import net.javaguides.banking_app.DTO.TransactionDto;
import net.javaguides.banking_app.DTO.TransferFundDto;
import net.javaguides.banking_app.entity.Account;
import net.javaguides.banking_app.entity.Transaction;
import net.javaguides.banking_app.exception.AccountExcepttion;
import net.javaguides.banking_app.exception.InsufficientBalanceException;
import net.javaguides.banking_app.mapper.AccountMApper;
import net.javaguides.banking_app.repository.AccountRepository;
import net.javaguides.banking_app.repository.TransactionRepository;
import net.javaguides.banking_app.service.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private static final String TRANSACTION_TYPE_DEPOSIT="DEPOSIT";
    private static final String TRANSACTION_TYPE_WITHDRAW="WITHDRAW";
    private static final String TRANSACTION_TYPE_TRANSFER="TRANSFER";
    public AccountServiceImpl(AccountRepository accountRepository,TransactionRepository transactionRepository){
        this.accountRepository=accountRepository;
        this.transactionRepository=transactionRepository;
    }
    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account= AccountMApper.mapToAccount(accountDto);
        Account saveAccount=accountRepository.save(account);
        return AccountMApper.maptoAccountDto(saveAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account=accountRepository
                .findById(id)
                .orElseThrow(()->new AccountExcepttion("Account does not exists"));
        return AccountMApper.maptoAccountDto(account);
    }

    @Override
    public AccountDto deposite(Long id, double amount) {
        Account account=accountRepository
                .findById(id)
                .orElseThrow(()->new AccountExcepttion("Account does not exists"));
         double total=account.getBalance()+amount;
         account.setBalance(total);
         Account savedAccount=accountRepository.save(account);
        Transaction transaction=new Transaction();
        transaction.setAccountId(id);
        transaction.setAmount(amount);
        transaction.setTransactionType(TRANSACTION_TYPE_DEPOSIT);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
         return AccountMApper.maptoAccountDto(savedAccount);

    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account=accountRepository
                .findById(id)
                .orElseThrow(()->new AccountExcepttion("Account does not exists"));
        if(account.getBalance()<amount){
            throw new RuntimeException("Insufficient amount");
        }
        double total=account.getBalance()-amount;
        account.setBalance(total);
        Account savedAccount=accountRepository.save(account);
        Transaction transaction=new Transaction();
        transaction.setAccountId(id);
        transaction.setAmount(amount);
        transaction.setTransactionType(TRANSACTION_TYPE_WITHDRAW);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        return AccountMApper.maptoAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts=accountRepository.findAll();
        return accounts.stream().map((account)->AccountMApper.maptoAccountDto(account))
                .collect(Collectors.toList());

    }

    @Override
    public void deleteAccount(Long id) {
        Account account=accountRepository
                .findById(id)
                .orElseThrow(()->new AccountExcepttion("Account does not exists"));
        accountRepository.deleteById(id);
    }

    @Override
    public void transferFunds(TransferFundDto transferFundDto) {
       //Retrive the account from which we send the amount
        Account fromAccount=accountRepository
                .findById(transferFundDto.fromAccountId())
                .orElseThrow(()->new AccountExcepttion("Account does not exist"));


        //Retrive the account to which we send the amount
        Account toAccount=accountRepository
                .findById(transferFundDto.toAccountId())
                .orElseThrow(()->new AccountExcepttion("Account does not exist"));
        if (fromAccount.getBalance() < transferFundDto.amount()) {
            throw new InsufficientBalanceException("Insufficient Amount");
        }
        //Debit the amount fromAccount object
        fromAccount.setBalance(fromAccount.getBalance()-transferFundDto.amount());

        //credit the amount toAccount object
        toAccount.setBalance(toAccount.getBalance()+transferFundDto.amount());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        Transaction transaction=new Transaction();
        transaction.setAccountId(transferFundDto.fromAccountId());
        transaction.setAmount(transferFundDto.amount());
        transaction.setTransactionType(TRANSACTION_TYPE_TRANSFER);
        transaction.setTimestamp(LocalDateTime.now());
         transactionRepository.save(transaction);

    }

    @Override
    public List<TransactionDto> getAccountTransactions(Long accountId) {
        List<Transaction> transactions=transactionRepository
                .findByAccountIdOrderByTimestampDesc(accountId);
        return transactions.stream()
                .map((transaction)->convertEntityToDto(transaction))
                .collect(Collectors.toList());
    }

    private  TransactionDto convertEntityToDto(Transaction transaction){
        return new TransactionDto(
                transaction.getId(),
                transaction.getAccountId(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getTimestamp()
        );
    }
}
