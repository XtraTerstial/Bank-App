package repository;

import domain.Transactions;

import java.util.*;

public class TransactionRepository {
    private final Map<String, List<Transactions>> txByAccount = new HashMap<>();

    public void add(Transactions transactions) {
        List<Transactions> list = txByAccount.computeIfAbsent(transactions.getAccountNumber(),
                k -> new ArrayList<>());
        list.add(transactions);
    }

    public List<Transactions> findByAccount(String account) {
        return new ArrayList<>(txByAccount.getOrDefault(account,Collections.emptyList()));
    }
}
