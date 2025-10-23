package service;

import domain.Account;
import domain.Customer;
import domain.Transactions;
import domain.enums.Type;
import exceptions.AccountNotFoundException;
import exceptions.InsufficientFundException;
import exceptions.ValidationException;
import repository.AccountRepository;
import repository.CustomerRepository;
import repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class BankServiceImpl implements BankService{

    private final AccountRepository accountRepository = new AccountRepository();
    private final TransactionRepository transactionRepository = new TransactionRepository();
    private final CustomerRepository customerRepository = new CustomerRepository();

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
                .collect(Collectors.toList());
    }

    @Override
    public void deposit(String accountNumber, Double amount, String note) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: "+accountNumber));
        account.setBalance(account.getBalance() + amount);
        Transactions transactions = new Transactions(account.getAccountNumber(),
                amount, UUID.randomUUID().toString(), note, LocalDateTime.now(), Type.DEPOSIT);
        transactionRepository.add(transactions);

    }

    @Override
    public void withdraw(String accountNumber, Double amount, String note) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(()->new AccountNotFoundException("Account not found: "+accountNumber));
        if(account.getBalance().compareTo(amount) < 0)
            throw new InsufficientFundException("Insufficient Balance: "+ account.getBalance());
        account.setBalance(account.getBalance() - amount);
        Transactions transactions = new Transactions(account.getAccountNumber(),
                amount, UUID.randomUUID().toString(), note, LocalDateTime.now(), Type.WITHDRAW);
        transactionRepository.add(transactions);
    }

    @Override
    public void transfer(String fromAcc, String toAcc, Double amount, String note) {
        if(fromAcc.equals(toAcc))
            throw new RuntimeException("Cannot transfer to the same Account");
        Account from = accountRepository.findByNumber(fromAcc)
                .orElseThrow(()->new ValidationException("Account not found: "+fromAcc));
        Account to = accountRepository.findByNumber(toAcc)
                .orElseThrow(()->new ValidationException("Account not found: "+toAcc));
        if(from.getBalance().compareTo(amount) < 0)
            throw new InsufficientFundException("Insufficient Balance");

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        transactionRepository.add(new Transactions(from.getAccountNumber(),
                amount, UUID.randomUUID().toString(), note, LocalDateTime.now(), Type.TRANSFER_OUT));

        transactionRepository.add(new Transactions(to.getAccountNumber(),
                amount, UUID.randomUUID().toString(), note, LocalDateTime.now(), Type.TRANSFER_IN));
    }

    @Override
    public List<Transactions> getStatement(String account) {
        return transactionRepository.findByAccount(account).stream()
                .sorted(Comparator.comparing(Transactions::getTimestamp))
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> searchAccountByCustomerName(String q) {
        String query = (q == null) ? "" : q.toLowerCase();
        List<Account> result = new ArrayList<>();
        for(Customer c: customerRepository.findAll()){
            if(c.getName().toLowerCase().contains(query))
                result.add(accountRepository.findByCustomerId(c.getId()));
        }
        result.sort(Comparator.comparing(Account::getAccountNumber));

        //Shorter version of above
//        return customerRepository.findAll().stream()
//                .filter(c -> c.getName().toLowerCase().contains(query))
//                .flatMap(c -> accountRepository.findByCustomerId(c.getId()).stream())
//                .sorted(Comparator.comparing(Account::getAccountNumber))
//                .collect(Collectors.toList());
        return result;
    }

    private String getAccountNumber() {
        int size = accountRepository.findAll().size()+1;
        return String.format("AC%06d", size);
    }
}
