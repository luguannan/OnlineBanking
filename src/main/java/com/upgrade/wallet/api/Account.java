package com.upgrade.wallet.api;

import com.upgrade.wallet.exception.IllegalAmountException;
import com.upgrade.wallet.exception.InsufficientFundException;

import java.util.List;

/**
 * Created by luguannan on 2018/11/7.
 */
public interface Account {
    /**
     * Get tn account id.
     * @return the account ID
     */
    String getId();

    /**
     * Make a deposit action.
     * @param value the amount to deposit
     * @throws IllegalAmountException when the amount is negative or has more than 2 decimals
     */
    void deposit(double value) throws IllegalAmountException;

    /**
     * Make a withdraw action.
     * @param value the amount to withdraw
     * @throws InsufficientFundException when the amount is larger than the balance.
     * @throws IllegalAmountException when the amount is negative or has more than 2 decimals
     */
    void withdraw(double value) throws InsufficientFundException,IllegalAmountException;

    /**
     * Get the balance of an account.
     * @return the amount of balance
     */
    double getBalance();

    /**
     * Get the last n transaction history.
     * @param n the number of transactions to be displayed.
     * @return the list of n transactions.
     */
    List<Transaction> getLastNTransactions(int n);
}
