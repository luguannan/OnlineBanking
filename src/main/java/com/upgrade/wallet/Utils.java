package com.upgrade.wallet;

import com.upgrade.wallet.exception.IllegalAmountException;
import com.upgrade.wallet.exception.InsufficientFundException;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by luguannan on 2018/11/7.
 */
public class Utils {
    /**
     * Generate IDs in random String.
     * @return String ID
     */
    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Validate the transaction amount.
     * @param value the transaction amount
     * @throws IllegalAmountException when the amount has more than 2 decimals or non-positive
     */
    public static void validateAmount(double value) throws IllegalAmountException {
        if(value <= 0){
            throw new IllegalAmountException("Deposit Amount Should Be Positive");
        }
        String valueStr = String.valueOf(value);
        if(valueStr.length() - 1 - valueStr.indexOf('.') > 2){
            throw new IllegalAmountException("Deposit Amount should contain at most 2 decimals");
        }
    }

    /**
     * Validate if the balance will be less than 0 after the transaction.
     * @param remaining current balance
     * @param withdraw the amount of money to be withdraw or transferred
     * @throws InsufficientFundException the balance is not sufficient for this transaction
     */
    public static void validateFund(double remaining, double withdraw) throws InsufficientFundException {
        if (remaining - withdraw < 0) {
            throw new InsufficientFundException();
        }
    }

    /**
     * Add the two values.
     * @param balance current balance
     * @param amount transaction amount
     * @return balance after transaction
     */
    public static double add(double balance, double amount){
        BigDecimal result = new BigDecimal(0);
        result = result.add(new BigDecimal(String.valueOf(balance)));
        result = result.add(new BigDecimal(String.valueOf(amount)));
        return result.doubleValue();
    }

    /**
     * Subtract some amount from current balance
     * @param balance current balance
     * @param amount amount to withdraw or transfer
     * @return balance after transaction
     */
    public static double subtract(double balance, double amount){
        BigDecimal result = new BigDecimal(0);
        result = result.add(new BigDecimal(String.valueOf(balance)));
        result = result.subtract(new BigDecimal(String.valueOf(amount)));
        return result.doubleValue();
    }
}
