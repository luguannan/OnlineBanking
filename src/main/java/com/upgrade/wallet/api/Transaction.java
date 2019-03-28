package com.upgrade.wallet.api;

import java.util.Date;

/**
 * Created by luguannan on 2018/11/7.
 */
public class Transaction {

    TransactionType type;
    String id;
    double amount;
    String fromAccount;
    String toAccount;
    Date time;

    public Transaction(TransactionType type, String id, double amount, String fromAccount, String toAccount, Date time) {
        this.type = type;
        this.id = id;
        this.amount = amount;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.time = time;
    }

    /**
     * 3 different types of transaction
     */
    public enum TransactionType {
        DEPOSIT,
        WITHDRAW,
        TRANSFER
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public Date getTime() { return time; }

    public void setTime(Date time) { this.time = time;}

    @Override
    public String toString() {
        return "Transaction{" +
                "type=" + type +
                ", id='" + id + '\'' +
                ", amount=" + amount +
                ", fromAccount='" + fromAccount + '\'' +
                ", toAccount='" + toAccount + '\'' +
                '}';
    }
}
