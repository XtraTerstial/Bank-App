package service;

import domain.Account;
import domain.Transactions;

import java.util.List;

public interface BankService {
    String openAccount(String name, String email, String accountType);

    List<Account> listAccounts();

    void deposit(String accountNumber, Double amount, String note);

    void withdraw(String accountNumber, Double amount, String withdrawal);

    void transfer(String from, String to, Double amount, String transfer);

    List<Transactions> getStatement(String account);

    List<Account> searchAccountByCustomerName(String q);
}

