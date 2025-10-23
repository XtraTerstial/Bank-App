package app;

import java.util.*;

public class Main {
    void main(){
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to Console Bank");
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
    }
}
