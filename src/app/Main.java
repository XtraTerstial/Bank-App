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
                case "3" -> withdraw(in);
                case "4" -> transfer(in);
                case "5" -> statement(in);
                case "6" -> listAccounts(in, bankService);
                case "7" -> searchAccounts(in);
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
        Double initial = Double.valueOf(amountStr);

        String accountNumber = bankService.openAccount(name, email, type);
        if(initial>0)
            bankService.deposit();
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

    private void withdraw(Scanner in) {
    }

    private void transfer(Scanner in) {
    }

    private void statement(Scanner in) {
    }

    private void listAccounts(Scanner in, BankService bankService) {
        bankService.listAccounts().forEach(a -> {
            System.out.println(a.getAccountNumber()+ " | " + a.getAccountType()+ " | " +a.getBalance());
        });
    }

    private void searchAccounts(Scanner in) {
    }
}
