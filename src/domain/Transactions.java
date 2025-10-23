package domain;

import domain.enums.Type;

import java.time.LocalDateTime;

public class Transactions {
    private String id;
    private Type type;
    private String accountNumber;
    private Double amount;
    private LocalDateTime timestamp;
    private String note;

    public Transactions(String accountNumber, Double amount, String id,String note, LocalDateTime now, Type type) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.id = id;
        this.note = note;
        this.timestamp = now;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
