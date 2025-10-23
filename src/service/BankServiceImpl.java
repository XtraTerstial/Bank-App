package service;

import domain.Account;
import domain.Transactions;
import domain.enums.Type;
import repository.AccountRepository;
import repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.*;

public class BankServiceImpl implements BankService{

    private final AccountRepository accountRepository = new AccountRepository();
    private final TransactionRepository transactionRepository = new TransactionRepository();

    @Override
    public String openAccount(String name, String email, String accountType) {
        String customerId = UUID.randomUUID().toString();
        // TODO:- Change Later --> 10+1 = AC000011 --> AC<06>
//        String accountNumber = UUID.randomUUID().toString();
        String accountNumber = getAccountNumber();
        Account account = new Account(accountNumber, accountType, (double) 0,customerId);
        //SAVE
        accountRepository.save(account);
        return accountNumber;
    }

    @Override
    public List<Account> listAccounts() {
        return accountRepository.findAll().stream()
                .sorted(Comparator.comparing(Account::getAccountNumber))
                .toList();
    }

    @Override
    public void deposit(String accountNumber, Double amount, String note) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found: "+accountNumber));
        account.setBalance(account.getBalance() + amount);
        Transactions transactions = new Transactions(account.getAccountNumber(),
                amount, UUID.randomUUID().toString(), note, LocalDateTime.now(), Type.DEPOSIT);
        transactionRepository.add(transactions);

    }

    private String getAccountNumber() {
        int size = accountRepository.findAll().size()+1;
        return String.format("AC%06d", size);
    }
}
