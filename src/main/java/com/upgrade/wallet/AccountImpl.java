package com.upgrade.wallet;

import com.upgrade.wallet.api.Account;
import com.upgrade.wallet.api.Transaction;
import com.upgrade.wallet.exception.IllegalAmountException;
import com.upgrade.wallet.exception.InsufficientFundException;
import com.upgrade.wallet.exception.NoSuchAccountException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by luguannan on 2018/11/7.
 */
public class AccountImpl implements Account {

    String id;
    double balance;
    ISBankImpl bank;
    List<Transaction> transactionLog;

    protected AccountImpl(String id, double balance, ISBankImpl bank) {
        this.id = id;
        this.balance = balance;
        this.bank = bank;
        transactionLog = new ArrayList<>();
    }

    /**
     * Get the account id.
     * @return the account id
     */
    public String getId() {
        return id;
    }

    /**
     * Make a deposit to the account.
     * @param value the amount to deposit
     * @throws IllegalAmountException throws when the amount is not valid.
     */
    public synchronized void deposit(double value) throws IllegalAmountException {
        Utils.validateAmount(value);
        // create transction log
        Transaction t = new Transaction(Transaction.TransactionType.DEPOSIT,
                Utils.generateId(), value, null, id, new Date());
        balance = Utils.add(balance, value);
        transactionLog.add(t);
    }

    /**
     * Make a withdraw transaction to the account.
     * @param value the amount to withdraw
     * @throws InsufficientFundException throws when the current balance is not sufficient.
     * @throws IllegalAmountException throws if the amount is not valid.
     */
    public synchronized void withdraw(double value) throws InsufficientFundException, IllegalAmountException {
        Utils.validateAmount(value);
        Utils.validateFund(balance, value);
        Transaction t = new Transaction(Transaction.TransactionType.WITHDRAW,
                Utils.generateId(), value, id, null, new Date());
        balance = Utils.subtract(balance, value);
        transactionLog.add(t);

    }

    /**
     * Make a transfer transaction from this account.
     * @param otherId the other account id that is to be transferred.
     * @param value the transfer amount
     * @throws InsufficientFundException the account balance is not sufficient
     * @throws IllegalAmountException the amount is not valid
     * @throws NoSuchAccountException throws when the other account to be transferred is not existed.
     */
    public synchronized void transfer(String otherId, double value) throws InsufficientFundException, IllegalAmountException, NoSuchAccountException {
        Utils.validateAmount(value);
        AccountImpl receiver = bank.getAccount(otherId);
        String tid = Utils.generateId();
        Date time = new Date();
        this.makeTransfer(otherId, value, tid, time);
        receiver.receiveTransfer(id, value, tid, time);
    }

    /**
     * Transfer to others.
     * @param toId the account id to be received
     * @param value the transfer amount
     * @param transactionId the transaction id to distinguish with other transaction logs
     * @param date the transfer time
     * @throws IllegalAmountException the amount is not valid
     * @throws InsufficientFundException the balance is not sufficient
     */
    protected void makeTransfer(String toId, double value, String transactionId, Date date) throws IllegalAmountException, InsufficientFundException {
        Utils.validateAmount(value);
        Utils.validateFund(balance, value);
        balance = Utils.subtract(balance, value);
        Transaction transaction = new Transaction(Transaction.TransactionType.TRANSFER,
                transactionId, value, id, toId, date);
        transactionLog.add(transaction);

    }

    /**
     * Receive a transferred money from others.
     * @param fromId the transfer from account id
     * @param value the transfer value
     * @param transactionId the transaction id to distinguish with other transaction logs
     * @param date the transaction time
     * @throws IllegalAmountException the amount is not valid
     */
    protected void receiveTransfer( String fromId, double value, String transactionId, Date date) throws IllegalAmountException {
        Utils.validateAmount(value);
        balance = Utils.add(balance,value);
        Transaction transaction = new Transaction(Transaction.TransactionType.TRANSFER,
                transactionId, value, fromId, id, date);
        transactionLog.add(transaction);

    }

    /**
     * Get the balance of an account.
     * @return the current balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Get the last n transactions from this account.
     * @param n the number of transactions to be displayed.
     * @return a list of the last n transactions
     */
    public List<Transaction> getLastNTransactions(int n) {
        if (transactionLog.size() <= n) return transactionLog;
        else return transactionLog.subList(transactionLog.size() - n, transactionLog.size());
    }

    public static AccountImpl newAccount(ISBankImpl bank) {
        return new AccountImpl(Utils.generateId(), 0.0, bank);
    }
}
