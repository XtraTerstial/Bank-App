package app;

import service.BankService;
import service.BankServiceImpl;

import java.util.*;

public class Main {
    void main(){
        Scanner in = new Scanner(System.in);
        BankService bankService = new BankServiceImpl();
        boolean running = true;
        System.out.println("Welcome to Console Bank");
        while(running){
            System.out.println("""
                    1) Open Account
                    2) Deposit
                    3) Withdraw
                    4) Transfer
                    5) Account Statement
                    6) List Accounts
                    7) Search Accounts ny Customer Name
                    """);
            System.out.println("CHOOSE: ");
            String choice = in.nextLine().trim();

            switch (choice) {
                case "1" -> openAccount(in, bankService);
                case "2" -> deposit(in, bankService);
                case "3" -> withdraw(in, bankService);
                case "4" -> transfer(in, bankService);
                case "5" -> statement(in, bankService);
                case "6" -> listAccounts(in, bankService);
                case "7" -> searchAccounts(in, bankService);
                case "0" -> running = false;
            }
        }
    }

    private void openAccount(Scanner in, BankService bankService) {
        System.out.println("Customer name: ");
        String name = in.nextLine().trim();
        System.out.println("Customer email: ");
        String email = in.nextLine().trim();
        System.out.println("Account Type (SAVINGS/CURRENT: ");
        String type = in.nextLine().trim();
        System.out.println("Initial deposit (optional, blank for 0): ");
        String amountStr = in.nextLine().trim();
        if(amountStr.isBlank()) amountStr = "0";
        Double initial = Double.valueOf(amountStr);

        String accountNumber = bankService.openAccount(name, email, type);
        if(initial>0)
            bankService.deposit(accountNumber, initial, "INITIAL DEPOSIT");
        System.out.println("Account Opened: " + accountNumber);
    }

    private void deposit(Scanner in, BankService bankService) {
        System.out.println("Account Number: ");
        String accountNumber = in.nextLine().trim();
        System.out.println("Amount: ");
        Double amount = Double.valueOf(in.nextLine().trim());
        bankService.deposit(accountNumber, amount, "Deposit");
        System.out.println("Deposited");
    }

    private void withdraw(Scanner in, BankService bankService) {
        System.out.println("Account Number: ");
        String accountNumber = in.nextLine().trim();
        System.out.println("Amount: ");
        Double amount = Double.valueOf(in.nextLine().trim());
        bankService.withdraw(accountNumber, amount, "Withdrawal");
        System.out.println("Withdrawn");
    }

    private void transfer(Scanner in, BankService bankService) {
        System.out.println("From Account: ");
        String from = in.nextLine().trim();
        System.out.println("To Account: ");
        String to = in.nextLine().trim();
        System.out.println("Amount: ");
        Double amount = Double.valueOf(in.nextLine().trim());
        bankService.transfer(from, to, amount, "Transfer");
        System.out.println("Transferred");
    }

    private void statement(Scanner in, BankService bankService) {
        System.out.println("Account Number: ");
        String account = in.nextLine().trim();
        bankService.getStatement(account).forEach(t -> {
            System.out.println(t.getTimestamp() + " | " + t.getType() + " | " + t.getAmount() + " | " + t.getNote());
        });
    }

    private void listAccounts(Scanner in, BankService bankService) {
        bankService.listAccounts().forEach(a -> {
            System.out.println(a.getAccountNumber()+ " | " + a.getAccountType()+ " | " +a.getBalance());
        });
    }

    private void searchAccounts(Scanner in, BankService bankService) {
        System.out.println("Customer Name contains: ");
        String q = in.nextLine().trim();
        bankService.searchAccountByCustomerName(q).forEach((account ->
                System.out.println(account.getAccountNumber() +" | " + account.getAccountType() + " | " + account.getAccountType())));
    }
}
